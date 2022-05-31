package com.paymongo.parking.parkingslot.enums;

/**
 * Enum for parking slot. Contains the size and rate per size.
 *
 */
public enum ParkingSlotEnum {

	SMALL("SP", 20), MEDIUM("MP", 60), LARGE("LP", 100);

	String size;
	int rate;

	ParkingSlotEnum(String size, int rate) {
		this.size = size;
		this.rate = rate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
