package com.paymongo.parking.parkingslot.dto;

public class ParkingSlotDistanceFromEntryPointDto {

	private final long entryPointId;
	private final long parkingSlotId;
	private final int distance;

	public ParkingSlotDistanceFromEntryPointDto(long entryPointId, long parkingSlotId, int distance) {
		this.entryPointId = entryPointId;
		this.parkingSlotId = parkingSlotId;
		this.distance = distance;
	}

	public long getEntryPointId() {
		return entryPointId;
	}

	public long getParkingSlotId() {
		return parkingSlotId;
	}

	public int getDistance() {
		return distance;
	}

}
