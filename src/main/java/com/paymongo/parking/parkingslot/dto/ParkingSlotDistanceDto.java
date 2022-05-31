package com.paymongo.parking.parkingslot.dto;

/**
 * Data transfer object for the parking slot distance
 *
 */
public class ParkingSlotDistanceDto {

	private final String entryPointName;
	private final String parkingSlotName;
	private final int distance;

	/**
	 * Creates the {@link ParkingSlotDistanceDto}
	 * 
	 * @param entryPointName  name of the entry point
	 * @param parkingSlotName name of the parking slot
	 * @param distance        distance of the parking slot from the entry point
	 */
	public ParkingSlotDistanceDto(String entryPointName, String parkingSlotName, int distance) {
		this.entryPointName = entryPointName;
		this.parkingSlotName = parkingSlotName;
		this.distance = distance;
	}

	/**
	 * Gets the entry point name
	 * 
	 * @return entry point name
	 */
	public String getEntryPointName() {
		return entryPointName;
	}

	/**
	 * Gets the parking slot name
	 * 
	 * @return the parking slot name
	 */
	public String getParkingSlotName() {
		return parkingSlotName;
	}

	/**
	 * Gets the distance
	 * 
	 * @return distance
	 */
	public int getDistance() {
		return distance;
	}

}
