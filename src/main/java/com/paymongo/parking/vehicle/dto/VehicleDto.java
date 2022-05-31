package com.paymongo.parking.vehicle.dto;

public class VehicleDto {

	private final String plateNumber;
	private final String size;

	public VehicleDto(String plateNumber, String size) {
		this.plateNumber = plateNumber;
		this.size = size;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getSize() {
		return size;
	}

}
