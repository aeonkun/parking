package com.paymongo.parking.parkingcomplex.dto;

import java.time.LocalDateTime;

public class UnparkRequestDto {
	private final String plateNumber;
	private final LocalDateTime dateTimeUnparked;

	public UnparkRequestDto(String plateNumber, LocalDateTime dateTimeUnparked) {
		this.plateNumber = plateNumber;
		this.dateTimeUnparked = dateTimeUnparked;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public LocalDateTime getDateTimeUnparked() {
		return dateTimeUnparked;
	}

}
