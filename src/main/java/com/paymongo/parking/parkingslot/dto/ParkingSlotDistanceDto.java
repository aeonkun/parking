package com.paymongo.parking.parkingslot.dto;

public class ParkingSlotDistanceDto {

	private final String entryPointName;
	private final String parkingSlotName;
	private final int distance;

	public ParkingSlotDistanceDto(String entryPointName, String parkingSlotName, int distance) {
		this.entryPointName = entryPointName;
		this.parkingSlotName = parkingSlotName;
		this.distance = distance;
	}

	public String getEntryPointName() {
		return entryPointName;
	}

	public String getParkingSlotName() {
		return parkingSlotName;
	}

	public int getDistance() {
		return distance;
	}

}
