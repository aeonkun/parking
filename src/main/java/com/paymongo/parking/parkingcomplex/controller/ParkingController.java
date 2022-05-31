package com.paymongo.parking.parkingcomplex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymongo.parking.ResponseModel;
import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.parkingcomplex.domain.VehicleParkingDetails;
import com.paymongo.parking.parkingcomplex.dto.ParkRequestDto;
import com.paymongo.parking.parkingcomplex.dto.UnparkRequestDto;
import com.paymongo.parking.parkingcomplex.exception.VehicleParkingDetailsException;
import com.paymongo.parking.parkingcomplex.service.ParkingService;
import com.paymongo.parking.parkingslot.exception.ParkingSlotException;
import com.paymongo.parking.vehicle.VehicleException;

/**
 * Rest controller for the parking functionality.
 *
 */
@RestController
@RequestMapping(path = "parking")
public class ParkingController {

	private static final String FAILED = "failed";
	private static final String SUCCESS = "success";

	@Autowired
	ParkingService parkingService;

	/**
	 * Parks the vehicle.
	 * 
	 * @param parkRequestDto contains the details needed to park
	 *                       ({@link ParkRequestDto})
	 * @return {@link ResponseModel} containing the status and data
	 *         ({@link VehicleParkingDetails}) of the request.
	 * @throws ParkingSlotException
	 * @throws VehicleParkingDetailsException
	 * @throws EntryPointException
	 * @throws VehicleException
	 */
	@PostMapping("/assignment/park")
	public ResponseModel parkVehicle(@RequestBody ParkRequestDto parkRequestDto)
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {
		return new ResponseModel(SUCCESS, parkingService.parkVehicle(parkRequestDto));
	}

	/**
	 * Unparks the vehicle.
	 * 
	 * @param unparkRequestDto contains the details needed to unpark
	 *                         ({@link UnparkRequestDto})
	 * @return {@link ResponseModel} containing status and data
	 *         ({@link VehicleParkingDetails}) of the request.
	 * @throws VehicleException
	 * @throws VehicleParkingDetailsException
	 */
	@PostMapping("/assignment/unpark")
	public ResponseModel unparkVehicle(@RequestBody UnparkRequestDto unparkRequestDto)
			throws VehicleException, VehicleParkingDetailsException {
		return new ResponseModel(SUCCESS, parkingService.unparkVehicle(unparkRequestDto));
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
	 * Handler for {@link VehicleParkingDetailsException}
	 * 
	 * @param vehicleParkingDetailsException
	 * @return status and exception message
	 */
	@ExceptionHandler(VehicleParkingDetailsException.class)
	public ResponseModel handleParkingSlotException(VehicleParkingDetailsException vehicleParkingDetailsException) {
		return new ResponseModel(FAILED, vehicleParkingDetailsException.getMessage());
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
