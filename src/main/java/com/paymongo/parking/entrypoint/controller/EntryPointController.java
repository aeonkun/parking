package com.paymongo.parking.entrypoint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymongo.parking.ResponseModel;
import com.paymongo.parking.entrypoint.dto.EntryPointDto;
import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.entrypoint.service.EntryPointService;

@RestController
@RequestMapping(path = "parking/entry-points")
public class EntryPointController {

	private static final String FAILED = "failed";
	private static final String SUCCESS = "success";

	@Autowired
	EntryPointService entryPointService;

	@PostMapping()
	public ResponseModel createEntryPoint(@RequestBody List<EntryPointDto> entryPointDto) {

		return new ResponseModel(SUCCESS, entryPointService.createEntryPoints(entryPointDto));
	}

	@GetMapping()
	public ResponseModel getAllEntryPoints() {

		return new ResponseModel(SUCCESS, entryPointService.getAllEntryPoints());
	}

	@DeleteMapping()
	public ResponseModel deleteEntryPoints(@RequestBody List<EntryPointDto> entryPointDtos) throws EntryPointException {

		return new ResponseModel(SUCCESS, entryPointService.deleteEntryPoints(entryPointDtos));
	}

	@ExceptionHandler(Exception.class)
	public ResponseModel handleParkingSlotException(Exception exception) {
		return new ResponseModel(FAILED, exception.getMessage());
	}
}
