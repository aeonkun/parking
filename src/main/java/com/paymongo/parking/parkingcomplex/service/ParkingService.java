package com.paymongo.parking.parkingcomplex.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.entrypoint.service.EntryPointService;
import com.paymongo.parking.parkingcomplex.dao.VehicleParkingDetailsRepository;
import com.paymongo.parking.parkingcomplex.domain.VehicleParkingDetails;
import com.paymongo.parking.parkingcomplex.dto.ParkRequestDto;
import com.paymongo.parking.parkingcomplex.dto.UnparkRequestDto;
import com.paymongo.parking.parkingcomplex.exception.VehicleParkingDetailsException;
import com.paymongo.parking.parkingslot.dao.ParkingSlotDistanceRepository;
import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.parkingslot.enums.ParkingSlotEnum;
import com.paymongo.parking.parkingslot.exception.ParkingSlotException;
import com.paymongo.parking.parkingslot.service.ParkingSlotService;
import com.paymongo.parking.vehicle.VehicleException;
import com.paymongo.parking.vehicle.domain.Vehicle;
import com.paymongo.parking.vehicle.service.VehicleService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ParkingService {

	private static Logger logger = LoggerFactory.getLogger(ParkingService.class);

	@Autowired
	ParkingSlotDistanceRepository parkingSlotDistanceRepository;

	@Autowired
	VehicleParkingDetailsRepository vehicleParkingDetailsRepository;

	@Autowired
	EntryPointService entryPointService;

	@Autowired
	ParkingSlotService parkingSlotService;

	@Autowired
	VehicleService vehicleService;

	/**
	 * Parks the vehicle.
	 * 
	 * @param parkRequestDto contains the entry point name, vehicle details, and
	 *                       date time of parking.
	 * @return the details of the parking {@link VehicleParkingDetails}
	 * @throws ParkingSlotException
	 * @throws VehicleParkingDetailsException
	 * @throws EntryPointException
	 * @throws VehicleException
	 */
	public VehicleParkingDetails parkVehicle(ParkRequestDto parkRequestDto)
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		VehicleParkingDetails vehicleParkingDetails = new VehicleParkingDetails();

		Vehicle vehicle = vehicleService.createVehicle(parkRequestDto.getVehicleDto());

		if (!checkIfAlreadyParked(vehicle)) {
			ParkingSlot parkingSlot = parkingSlotService.assignParkingSlot(parkRequestDto.getEntryPointName(),
					vehicle.getSize());
			if (parkingSlot != null) {
				vehicleParkingDetails.setVehicle(vehicle);
				vehicleParkingDetails.setParkingSlot(parkingSlot);
				vehicleParkingDetails.setDateTimeParked(parkRequestDto.getDateTimeParked());

				vehicleParkingDetails = vehicleParkingDetailsRepository.save(vehicleParkingDetails);
			}
		} else {
			throw new VehicleParkingDetailsException(
					VehicleParkingDetailsException.VEHICLE_IS_ALREADY_PARKED_EXCEPTION);
		}

		return vehicleParkingDetails;
	}

	/**
	 * Unparks the vehicle.
	 * 
	 * @param unparkRequestDto contains the plate number of the vehicle and date
	 *                         time of departure.
	 * @return the details of the parking {@link VehicleParkingDetails}.
	 * @throws VehicleException
	 * @throws VehicleParkingDetailsException
	 */
	public VehicleParkingDetails unparkVehicle(UnparkRequestDto unparkRequestDto)
			throws VehicleException, VehicleParkingDetailsException {

		VehicleParkingDetails vehicleParkingDetails = vehicleParkingDetailsRepository
				.saveAndFlush(processVehicleUnparkRequest(unparkRequestDto));
		parkingSlotService.setParkingSlotToVacant(vehicleParkingDetails.getParkingSlot());

		return vehicleParkingDetails;

	}

	/**
	 * Processes the unpark request.
	 * 
	 * @param unparkRequestDto contains the plate number of the vehicle and date
	 *                         time of departure.
	 * @return the details of the parking {@link VehicleParkingDetails}.
	 * @throws VehicleException
	 * @throws VehicleParkingDetailsException
	 */
	public VehicleParkingDetails processVehicleUnparkRequest(UnparkRequestDto unparkRequestDto)
			throws VehicleException, VehicleParkingDetailsException {

		Vehicle vehicle = vehicleService.getVehicleByPlateNumber(unparkRequestDto.getPlateNumber());
		VehicleParkingDetails vehicleParkingDetails = null;
		int computedParkingFee = 0;

		if (vehicle != null) {
			vehicleParkingDetails = getVehicleParkingDetailsByVehiclePlateNumberAndDateTimeUnparked(
					vehicle.getPlateNumber(), null);

			if (vehicleParkingDetails != null) {
				VehicleParkingDetails previousParkingSlotAssignment = getPreviousParkingDetailsByVehiclePlateNumber(
						unparkRequestDto.getPlateNumber());

				if (previousParkingSlotAssignment != null) {

					computedParkingFee = computeParkingFee(vehicleParkingDetails.getParkingSlot().getSize(),
							vehicleParkingDetails.getDateTimeParked(), unparkRequestDto.getDateTimeUnparked(),
							previousParkingSlotAssignment.getDateTimeParked(),
							previousParkingSlotAssignment.getDateTimeUnparked());
				} else {
					computedParkingFee = computeParkingFee(vehicleParkingDetails.getParkingSlot().getSize(),
							vehicleParkingDetails.getDateTimeParked(), unparkRequestDto.getDateTimeUnparked(), null,
							null);
				}

				vehicleParkingDetails.setDateTimeUnparked(unparkRequestDto.getDateTimeUnparked());
				vehicleParkingDetails.setParkingFee(computedParkingFee);

			} else {
				throw new VehicleParkingDetailsException(
						VehicleParkingDetailsException.NO_ACTIVE_VEHICLE_PARKING_DETAILS_EXCEPTION);
			}

		} else {
			throw new VehicleException(VehicleException.VEHICLE_NOT_FOUND_EXCEPTION);

		}

		return vehicleParkingDetails;

	}

	/**
	 * Computes the parking fee based on the parking slot size and duration of
	 * parking.
	 * 
	 * @param parkingSlotSize      size of the parking slot.
	 * @param dateTimeParked       date and time when the vehicle parked.
	 * @param dateTimeUnparked     date and time when the vehicle departed.
	 * @param prevDateTimeParked   date and time parked from the previous record of
	 *                             the vehicle.
	 * @param prevDateTimeUnparked date and time departed from the previous record
	 *                             of the vehicle.
	 * @return the total computed parking fee.
	 */
	private int computeParkingFee(String parkingSlotSize, LocalDateTime dateTimeParked, LocalDateTime dateTimeUnparked,
			LocalDateTime prevDateTimeParked, LocalDateTime prevDateTimeUnparked) {

		logger.info("parked: {}", dateTimeParked);
		LocalDateTime start = dateTimeParked;
		LocalDateTime end = dateTimeUnparked;
		boolean isContinuous = false;

		// check if continuous rate should apply

		if (prevDateTimeParked != null && prevDateTimeUnparked != null
				&& getTimeDifference(prevDateTimeUnparked, start) <= 1) {
			start = prevDateTimeParked;
			isContinuous = true;
			logger.info("continuous time diff: {}", ChronoUnit.HOURS.between(start, prevDateTimeUnparked));
		}

		int timeDifference = (int) Math.ceil(getTimeDifference(start, end));
		int rate = getRatePerHour(parkingSlotSize);
		int computedParkingFee = 0;

		if (timeDifference <= 3 && !isContinuous) {
			computedParkingFee = 40;
		} else if (timeDifference > 3 && timeDifference < 24) {
			if (isContinuous) {
				computedParkingFee = (timeDifference - 3) * rate;
			} else {
				computedParkingFee = 40 + ((timeDifference - 3) * rate);
			}
		} else if (timeDifference >= 24) {
			computedParkingFee = (5000 * (timeDifference / 24)) + (rate * (timeDifference % 24));
		}

		logger.info(
				"Computing parking fee: time parked: {} time unparked: {} time difference: {} parking slot size: {} rate per hour: {} computed parking fee: {}",
				start, end, timeDifference, parkingSlotSize, rate, computedParkingFee);

		return computedParkingFee;

	}

	/**
	 * Get the {@link VehicleParkingDetails} based on the plate number and date time
	 * of departure.
	 * 
	 * @param plateNumber      plate number of the vehicle.
	 * @param dateTimeUnparked date and time of departure.
	 * @return {@link VehicleParkingDetails}
	 */
	private VehicleParkingDetails getVehicleParkingDetailsByVehiclePlateNumberAndDateTimeUnparked(String plateNumber,
			LocalDateTime dateTimeUnparked) {
		Optional<VehicleParkingDetails> parkingSlotAssignment = vehicleParkingDetailsRepository
				.findByVehiclePlateNumberAndDateTimeUnparked(plateNumber, dateTimeUnparked);

		if (!parkingSlotAssignment.isPresent()) {
			return null;
		}

		return parkingSlotAssignment.get();
	}

	/**
	 * Gets the previous parking details of the vehicle with the provided plate
	 * number.
	 * 
	 * @param plateNumber of the vehicle.
	 * @return {@link VehicleParkingDetails}
	 */
	public VehicleParkingDetails getPreviousParkingDetailsByVehiclePlateNumber(String plateNumber) {
		Optional<VehicleParkingDetails> parkingSlotAssignment = vehicleParkingDetailsRepository
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc(plateNumber);

		if (!parkingSlotAssignment.isPresent()) {
			return null;
		}

		logger.info("prevUnparked: {} ", parkingSlotAssignment.get().getDateTimeUnparked());
		return parkingSlotAssignment.get();
	}

	/**
	 * Gets the rate per hour depending on the size of the parking slot.
	 * 
	 * @param size of the parking slot.
	 * @return rate per hour.
	 */
	private int getRatePerHour(String size) {
		int rate = 0;

		switch (size) {
		case "SP":
			rate = ParkingSlotEnum.SMALL.getRate();
			break;
		case "MP":
			rate = ParkingSlotEnum.MEDIUM.getRate();
			break;
		case "LP":
			rate = ParkingSlotEnum.LARGE.getRate();
			break;
		default:
			logger.error("Invalid parking slot size: {}", size);
		}
		return rate;
	}

	/**
	 * Computes the time difference (hours) between the provided dates.
	 * 
	 * @param start start of the date time period
	 * @param end   end of the date time period
	 * @return time difference in hours
	 */
	private double getTimeDifference(LocalDateTime start, LocalDateTime end) {

		return ChronoUnit.SECONDS.between(start, end) / 3600d;
	}

	/**
	 * Checks if the vehicle is already parked.
	 * 
	 * @param vehicle vehicle to be checked.
	 * @return true if the vehicle is parked. false if not.
	 */
	private boolean checkIfAlreadyParked(Vehicle vehicle) {
		boolean isParked = false;
		VehicleParkingDetails vehicleParkingDetails = getVehicleParkingDetailsByVehiclePlateNumberAndDateTimeUnparked(
				vehicle.getPlateNumber(), null);

		if (vehicleParkingDetails != null) {
			isParked = true;
		}

		return isParked;
	}

}
