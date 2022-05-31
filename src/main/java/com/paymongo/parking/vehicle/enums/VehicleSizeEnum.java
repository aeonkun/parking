package com.paymongo.parking.vehicle.enums;

public enum VehicleSizeEnum {

	SMALL("S"), MEDIUM("M"), LARGE("L");

	private final String size;

	VehicleSizeEnum(String size) {
		this.size = size;
	}

	public String getSize() {
		return size;
	}

}
