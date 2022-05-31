package com.paymongo.parking.parkingcomplex.dto;

import java.time.LocalDateTime;

import com.paymongo.parking.vehicle.dto.VehicleDto;

/**
 * Data transfer object for park requests
 *
 */
public class ParkRequestDto {

	private final String entryPointName;
	private final VehicleDto vehicleDto;
	private final LocalDateTime dateTimeParked;

	/**
	 * Creates the {@link ParkRequestDto} with the provided data.
	 * 
	 * @param entryPointName name of the entry point.
	 * @param vehicleDto     details of the vehicle.
	 * @param dateTimeParked date and time of parking.
	 */
	public ParkRequestDto(String entryPointName, VehicleDto vehicleDto, LocalDateTime dateTimeParked) {
		this.entryPointName = entryPointName;
		this.vehicleDto = vehicleDto;
		this.dateTimeParked = dateTimeParked;
	}

	/**
	 * Gets the entry point name.
	 * 
	 * @return name of the entry point.
	 */
	public String getEntryPointName() {
		return entryPointName;
	}

	/**
	 * Gets the details of the vehicle {@link VehicleDto}.
	 * 
	 * @return details of the vehicle {@link VehicleDto}.
	 */
	public VehicleDto getVehicleDto() {
		return vehicleDto;
	}

	/**
	 * Gets the date and time of parking.
	 * 
	 * @return date and time of parking.
	 */
	public LocalDateTime getDateTimeParked() {
		return dateTimeParked;
	}

}
