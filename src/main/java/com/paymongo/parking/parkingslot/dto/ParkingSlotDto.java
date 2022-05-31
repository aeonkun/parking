package com.paymongo.parking.parkingslot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for parking slot
 * 
 *
 */
public class ParkingSlotDto {

	private final String name;

	private final String size;

	/**
	 * Creates the {@link ParkingSlotDto}
	 * 
	 * @param name name of the parking slot.
	 * @param size size of the parking slot.
	 */
	@JsonCreator
	public ParkingSlotDto(@JsonProperty("name") String name, @JsonProperty("size") String size) {
		this.name = name;
		this.size = size;
	}

	/**
	 * Gets the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the size
	 * 
	 * @return size
	 */
	public String getSize() {
		return size;
	}

}
