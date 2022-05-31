package com.paymongo.parking.parkingcomplex.dto;

import java.time.LocalDateTime;

import com.paymongo.parking.vehicle.dto.VehicleDto;

public class ParkRequestDto {

	private final String entryPointName;
	private final VehicleDto vehicleDto;
	private final LocalDateTime dateTimeParked;

	public ParkRequestDto(String entryPointName, VehicleDto vehicleDto, LocalDateTime dateTimeParked) {
		this.entryPointName = entryPointName;
		this.vehicleDto = vehicleDto;
		this.dateTimeParked = dateTimeParked;
	}

	public String getEntryPointName() {
		return entryPointName;
	}

	public VehicleDto getVehicleDto() {
		return vehicleDto;
	}

	public LocalDateTime getDateTimeParked() {
		return dateTimeParked;
	}

}
