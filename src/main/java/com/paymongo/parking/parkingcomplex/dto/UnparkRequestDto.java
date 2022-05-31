package com.paymongo.parking.parkingcomplex.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object for the unpark request.
 *
 */
public class UnparkRequestDto {
	private final String plateNumber;
	private final LocalDateTime dateTimeUnparked;

	/**
	 * Creates the {@link UnparkRequestDto}.
	 * 
	 * @param plateNumber      plate number of the vehicle.
	 * @param dateTimeUnparked date and time of departure.
	 */
	public UnparkRequestDto(String plateNumber, LocalDateTime dateTimeUnparked) {
		this.plateNumber = plateNumber;
		this.dateTimeUnparked = dateTimeUnparked;
	}

	/**
	 * Gets the plate number of the vehicle.
	 * 
	 * @return plate number of the vehicle.
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * Gets the date and time when the vehicle departed.
	 * 
	 * @return date and time of departure.
	 */
	public LocalDateTime getDateTimeUnparked() {
		return dateTimeUnparked;
	}

}
