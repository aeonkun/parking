package com.paymongo.parking.parkingslot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymongo.parking.ResponseModel;
import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.parkingslot.domain.ParkingSlotDistance;
import com.paymongo.parking.parkingslot.dto.ParkingSlotDistanceDto;
import com.paymongo.parking.parkingslot.dto.ParkingSlotDto;
import com.paymongo.parking.parkingslot.exception.ParkingSlotException;
import com.paymongo.parking.parkingslot.service.ParkingSlotService;
import com.paymongo.parking.vehicle.VehicleException;

/**
 * Rest controller for parking slot.
 *
 */
@RestController
@RequestMapping(path = "parking/parking-slots")
public class ParkingSlotController {

	private static final String FAILED = "failed";
	private static final String SUCCESS = "success";

	@Autowired
	ParkingSlotService parkingSlotService;

	/**
	 * Get all parking slots
	 * 
	 * @return status code and list of {@link ParkingSlot}
	 */
	@GetMapping
	public ResponseModel getAllParkingSlots() {
		return new ResponseModel(SUCCESS, parkingSlotService.getAllParkingSlots());
	}

	/**
	 * Create and save the {@link ParkingSlot} from the {@link ParkingSlotDto} list.
	 * 
	 * @param parkingSlotDtos list of parking slots with the necessary details to be
	 *                        created ({@link ParkingSlotDto}) .
	 * @return
	 * @throws EntryPointException
	 */
	@PostMapping()
	public ResponseModel createParkingSlots(@RequestBody List<ParkingSlotDto> parkingSlotDtos)
			throws EntryPointException {

		return new ResponseModel(SUCCESS, parkingSlotService.createParkingSlots(parkingSlotDtos));
	}

	/**
	 * Gets the parking slot distance by entry point name.
	 * 
	 * @param entryPointName name of entry point
	 * @return status and list of {@link ParkingSlotDistance} for the provided entry
	 *         point.
	 */
	@GetMapping("/distances")
	public ResponseModel getParkingSlotDistanceByEntryPointName(@RequestParam String entryPointName) {

		return new ResponseModel(SUCCESS, parkingSlotService.getAllParkingSlotDistanceByEntryPointName(entryPointName));
	}

	/**
	 * Sets the parking slot distance from the list of
	 * {@link ParkingSlotDistanceDto} provided
	 * 
	 * @param parkingSlotDistanceDto list of {@link ParkingSlotDistanceDto}
	 * @return created {@link ParkingSlotDistance}
	 * @throws EntryPointException
	 * @throws ParkingSlotException
	 */
	@PostMapping("/distances")
	public ResponseModel setParkingSlotDistances(@RequestBody List<ParkingSlotDistanceDto> parkingSlotDistanceDto)
			throws EntryPointException, ParkingSlotException {
		return new ResponseModel(SUCCESS, parkingSlotService.setParkingSlotDistances(parkingSlotDistanceDto));
	}

	/**
	 * Handler for {@link EntryPointException}
	 * 
	 * @param entryPointException
	 * @return status and exception message
	 */
	@ExceptionHandler(EntryPointException.class)
	public ResponseModel handleEntryPointException(EntryPointException entryPointException) {
		return new ResponseModel(FAILED, entryPointException.getMessage());
	}

	/**
	 * Handler for {@link ParkingSlotException}
	 * 
	 * @param parkingSlotException
	 * @return status and exception message
	 */
	@ExceptionHandler(ParkingSlotException.class)
	public ResponseModel handleParkingSlotException(ParkingSlotException parkingSlotException) {
		return new ResponseModel(FAILED, parkingSlotException.getMessage());
	}

	/**
	 * Handler for {@link VehicleException}
	 * 
	 * @param vehicleException
	 * @return status and exception message
	 */
	@ExceptionHandler(VehicleException.class)
	public ResponseModel handleParkingSlotException(VehicleException vehicleException) {
		return new ResponseModel(FAILED, vehicleException.getMessage());
	}

}
