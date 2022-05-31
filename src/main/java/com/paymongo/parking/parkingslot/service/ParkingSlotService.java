package com.paymongo.parking.parkingslot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paymongo.parking.entrypoint.domain.EntryPoint;
import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.entrypoint.service.EntryPointService;
import com.paymongo.parking.parkingslot.dao.ParkingSlotDistanceRepository;
import com.paymongo.parking.parkingslot.dao.ParkingSlotRepository;
import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.parkingslot.domain.ParkingSlotDistance;
import com.paymongo.parking.parkingslot.dto.ParkingSlotDistanceDto;
import com.paymongo.parking.parkingslot.dto.ParkingSlotDto;
import com.paymongo.parking.parkingslot.enums.ParkingSlotEnum;
import com.paymongo.parking.parkingslot.exception.ParkingSlotException;
import com.paymongo.parking.vehicle.VehicleException;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ParkingSlotService {

	private static Logger logger = LoggerFactory.getLogger(ParkingSlotService.class);

	@Autowired
	ParkingSlotRepository parkingSlotRepository;

	@Autowired
	ParkingSlotDistanceRepository parkingSlotDistanceRepository;

	@Autowired
	EntryPointService entryPointService;

	/**
	 * Creates a {@link ParkingSlot} with the given parking slot data.
	 * 
	 * @param parkingSlotDto the parking slot data to be created.
	 * @return the {@link ParkingSlot} that was created.
	 * @throws EntryPointException if the given {@link EntryPoint} does not exist in
	 *                             the database.
	 */
	public ParkingSlot createParkingSlot(ParkingSlotDto parkingSlotDto) throws EntryPointException {
		ParkingSlot parkingSlot = new ParkingSlot();

		ParkingSlot existingParkingSlot = getParkingSlotByName(parkingSlotDto.getName());

		if (existingParkingSlot == null) {
			parkingSlot.setName(parkingSlotDto.getName());
			parkingSlot.setSize(parkingSlotDto.getSize());
			parkingSlot.setVacant(true);
			logger.info("Creating parking slot with name: {} size: {} isVacant: {}", parkingSlot.getName(),
					parkingSlot.getSize(), parkingSlot.isVacant());
		} else {
			parkingSlot = existingParkingSlot;
			parkingSlot.setName(parkingSlotDto.getName());
			parkingSlot.setSize(parkingSlotDto.getSize());
			logger.info("Updating parking slot with name: {} size: {} isVacant: {}", parkingSlot.getName(),
					parkingSlot.getSize(), parkingSlot.isVacant());
		}

		return parkingSlotRepository.saveAndFlush(parkingSlot);
	}

	/**
	 * Creates the parking slots from the given list of {@link ParkingSlotDto}.
	 * 
	 * @param parkingSlotDtos list of {@link ParkingSlotDto}.
	 * @return list of {@link ParkingSlot} created.
	 * @throws EntryPointException
	 */
	public List<ParkingSlot> createParkingSlots(List<ParkingSlotDto> parkingSlotDtos) throws EntryPointException {
		List<ParkingSlot> parkingSlots = new ArrayList<>();

		for (ParkingSlotDto parkingSlotDto : parkingSlotDtos) {
			parkingSlots.add(createParkingSlot(parkingSlotDto));
		}

		return parkingSlots;
	}

	/**
	 * Gets all parking slots from the database
	 * 
	 * @return list of {@link ParkingSlot}
	 */
	public List<ParkingSlot> getAllParkingSlots() {

		return parkingSlotRepository.findAll();
	}

	/**
	 * Gets the parking slot by name.
	 * 
	 * @param name of the parking slot
	 * @return {@link ParkingSlot}
	 */
	public ParkingSlot getParkingSlotByName(String name) {
		Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findByName(name);

		if (!parkingSlot.isPresent()) {
			return null;
		}

		return parkingSlot.get();
	}

	/**
	 * Sets the parking slot status to occupied.
	 * 
	 * @param parkingSlot to be set to occupied.
	 * @return {@link ParkingSlot}
	 */
	public ParkingSlot setParkingSlotToOccupied(ParkingSlot parkingSlot) {
		parkingSlot.setVacant(false);
		return parkingSlotRepository.saveAndFlush(parkingSlot);
	}

	/**
	 * Sets the parking slot status to vacant.
	 * 
	 * @param parkingSlot to be set to vacant
	 * @return {@link ParkingSlot}
	 */
	public ParkingSlot setParkingSlotToVacant(ParkingSlot parkingSlot) {
		parkingSlot.setVacant(true);
		return parkingSlotRepository.saveAndFlush(parkingSlot);
	}

	/**
	 * Gets the parking slot distance by entry point and parking slot from the
	 * database.
	 * 
	 * @param entryPoint  {@link EntryPoint}
	 * @param parkingSlot {@link ParkingSlot}
	 * @return
	 */
	public ParkingSlotDistance getParkingSlotDistanceByEntryPointAndParkingSlot(EntryPoint entryPoint,
			ParkingSlot parkingSlot) {

		Optional<ParkingSlotDistance> parkingSlotDistance = parkingSlotDistanceRepository
				.findByEntryPointAndParkingSlot(entryPoint, parkingSlot);

		if (!parkingSlotDistance.isPresent()) {
			return null;
		}

		return parkingSlotDistance.get();

	}

	/**
	 * Creates a {@link ParkingSlotDistance} object which contains the distance of
	 * the parking slot from a specific entry point.
	 * 
	 * @param {@link ParkingSlotDto} the parking slot distance details to be set.
	 * @return the created {@link ParkingSlotDistance} object.
	 * @throws EntryPointException  if the given entry point ID does not exist in
	 *                              the database.
	 * @throws ParkingSlotException if the given parking slot ID does not exist in
	 *                              the database
	 */
	public ParkingSlotDistance setParkingSlotDistance(ParkingSlotDistanceDto parkingSlotDistanceDto)
			throws EntryPointException, ParkingSlotException {

		EntryPoint entryPoint = entryPointService.getEntryPointByName(parkingSlotDistanceDto.getEntryPointName());
		if (entryPoint == null) {
			throw new EntryPointException(EntryPointException.ENTRY_POINT_NOT_FOUND_EXCEPTION);
		}
		ParkingSlot parkingSlot = getParkingSlotByName(parkingSlotDistanceDto.getParkingSlotName());
		if (parkingSlot == null) {
			throw new ParkingSlotException(ParkingSlotException.PARKING_SLOT_NOT_FOUND_EXCEPTION);
		}

		ParkingSlotDistance parkingSlotDistance;

		if (getParkingSlotDistanceByEntryPointAndParkingSlot(entryPoint, parkingSlot) == null) {
			parkingSlotDistance = new ParkingSlotDistance();
			parkingSlotDistance.setEntryPoint(entryPoint);
			parkingSlotDistance.setParkingSlot(parkingSlot);
			parkingSlotDistance.setDistance(parkingSlotDistanceDto.getDistance());

			parkingSlotDistance = parkingSlotDistanceRepository.save(parkingSlotDistance);
		} else {
			parkingSlotDistance = getParkingSlotDistanceByEntryPointAndParkingSlot(entryPoint, parkingSlot);
			parkingSlotDistance.setDistance(parkingSlotDistanceDto.getDistance());
		}

		return parkingSlotDistance;
	}

	/**
	 * Sets the parking slot distance
	 * 
	 * @param parkingSlotDistanceDtos list of {@link ParkingSlotDto} to be created.
	 * @return list of {@link ParkingSlotDistance} that was created.
	 * @throws EntryPointException
	 * @throws ParkingSlotException
	 */
	public List<ParkingSlotDistance> setParkingSlotDistances(List<ParkingSlotDistanceDto> parkingSlotDistanceDtos)
			throws EntryPointException, ParkingSlotException {

		List<ParkingSlotDistance> parkingSlotDistances = new ArrayList<>();

		for (ParkingSlotDistanceDto parkingSlotDistanceDto : parkingSlotDistanceDtos) {
			parkingSlotDistances.add(setParkingSlotDistance(parkingSlotDistanceDto));
		}
		return parkingSlotDistances;
	}

	/**
	 * Get all {@link ParkingSlotDistance} by entry point name.
	 * 
	 * @param entryPointName
	 * @return
	 */
	public List<ParkingSlotDistance> getAllParkingSlotDistanceByEntryPointName(String entryPointName) {
		EntryPoint entryPoint = entryPointService.getEntryPointByName(entryPointName);

		return parkingSlotDistanceRepository.findAllByEntryPoint(entryPoint);
	}

	/**
	 * Gets the nearest vacant parking slot from the entry point with the acceptable
	 * sizes.
	 * 
	 * @param entryPoint  {@link EntryPoint} that the vehicle entered from.
	 * @param vehicleSize size of the vehicle.
	 * @return the {@link ParkingSlot} to be assigned.
	 * @throws VehicleException
	 */
	public ParkingSlot getNearestVacantParkingSlotFromEntryPointWithAcceptableSize(EntryPoint entryPoint,
			String vehicleSize) throws VehicleException {
		List<String> acceptableParkingSizes = getAcceptableParkingSlotSizes(vehicleSize);

		Optional<ParkingSlotDistance> parkingSlotDistance = parkingSlotDistanceRepository
				.findFirstByEntryPointAndParkingSlotSizeInAndParkingSlotIsVacantTrueOrderByDistanceAsc(entryPoint,
						acceptableParkingSizes);
		if (!parkingSlotDistance.isPresent()) {
			return null;
		}
		return parkingSlotDistance.get().getParkingSlot();
	}

	/**
	 * Assigns a parking slot to a vehicle.
	 * 
	 * @param entryPointName
	 * @param vehicleSize
	 * @return
	 * @throws ParkingSlotException
	 * @throws EntryPointException
	 * @throws VehicleException
	 */
	public ParkingSlot assignParkingSlot(String entryPointName, String vehicleSize)
			throws ParkingSlotException, EntryPointException, VehicleException {

		ParkingSlot parkingSlot = null;

		EntryPoint entryPoint = entryPointService.getEntryPointByName(entryPointName);
		if (entryPoint != null) {
			parkingSlot = getNearestVacantParkingSlotFromEntryPointWithAcceptableSize(entryPoint, vehicleSize);

			if (parkingSlot == null) {
				throw new ParkingSlotException(ParkingSlotException.NO_AVAILABE_PARKING_SLOT_EXCEPTION);
			}
		} else {
			throw new EntryPointException(EntryPointException.ENTRY_POINT_NOT_FOUND_EXCEPTION);
		}

		return setParkingSlotToOccupied(parkingSlot);

	}

	/**
	 * Gets a list of acceptable parking slot size based on the vehicle size.
	 * 
	 * @param vehicleSize size of vehicle
	 * @return list of string containing the acceptable parking slot sizes for the
	 *         vehicle size.
	 * @throws VehicleException
	 */
	private List<String> getAcceptableParkingSlotSizes(String vehicleSize) throws VehicleException {

		List<String> acceptableParkingSlotSizes = new ArrayList<>();

		switch (vehicleSize) {
		case "S":
			acceptableParkingSlotSizes.add(ParkingSlotEnum.SMALL.getSize());
			acceptableParkingSlotSizes.add(ParkingSlotEnum.MEDIUM.getSize());
			acceptableParkingSlotSizes.add(ParkingSlotEnum.LARGE.getSize());
			break;
		case "M":
			acceptableParkingSlotSizes.add(ParkingSlotEnum.MEDIUM.getSize());
			acceptableParkingSlotSizes.add(ParkingSlotEnum.LARGE.getSize());
			break;
		case "L":
			acceptableParkingSlotSizes.add(ParkingSlotEnum.LARGE.getSize());
			break;
		default:
			logger.error("Invalid vehicle size: {}", vehicleSize);
			throw new VehicleException(VehicleException.INVALID_VEHICLE_SIZE);
		}

		return acceptableParkingSlotSizes;
	}
}
