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
import com.paymongo.parking.parkingslot.dto.ParkingSlotDistanceDto;
import com.paymongo.parking.parkingslot.dto.ParkingSlotDto;
import com.paymongo.parking.parkingslot.exception.ParkingSlotException;
import com.paymongo.parking.parkingslot.service.ParkingSlotService;

@RestController
@RequestMapping(path = "parking/parking-slots")
public class ParkingSlotController {

	private static final String FAILED = "failed";
	private static final String SUCCESS = "success";

	@Autowired
	ParkingSlotService parkingSlotService;

	@GetMapping
	public ResponseModel getAllParkingSlots() {
		return new ResponseModel(SUCCESS, parkingSlotService.getAllParkingSlots());
	}

	@PostMapping()
	public ResponseModel createParkingSlot(@RequestBody List<ParkingSlotDto> parkingSlotDtos)
			throws EntryPointException {

		return new ResponseModel(SUCCESS, parkingSlotService.createParkingSlots(parkingSlotDtos));
	}

	@GetMapping("/distances")
	public ResponseModel getParkingSlotDistanceByEntryPointId(@RequestParam String entryPointName) {

		return new ResponseModel(SUCCESS, parkingSlotService.getAllParkingSlotDistanceByEntryPointName(entryPointName));
	}

	@PostMapping("/distances")
	public ResponseModel setParkingSlotDistances(@RequestBody List<ParkingSlotDistanceDto> parkingSlotDistanceDto)
			throws EntryPointException, ParkingSlotException {
		return new ResponseModel(SUCCESS, parkingSlotService.setParkingSlotDistances(parkingSlotDistanceDto));
	}

	@ExceptionHandler(EntryPointException.class)
	public ResponseModel handleEntryPointException(EntryPointException entryPointException) {
		return new ResponseModel(FAILED, entryPointException.getMessage());
	}

	@ExceptionHandler(ParkingSlotException.class)
	public ResponseModel handleParkingSlotException(ParkingSlotException parkingSlotException) {
		return new ResponseModel(FAILED, parkingSlotException.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseModel handleParkingSlotException(Exception exception) {
		return new ResponseModel(FAILED, exception.getMessage());
	}

}
