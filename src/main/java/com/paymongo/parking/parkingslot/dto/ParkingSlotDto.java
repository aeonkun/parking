package com.paymongo.parking.parkingslot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingSlotDto {

	private final String name;

	private final String size;

	@JsonCreator
	public ParkingSlotDto(@JsonProperty("name") String name, @JsonProperty("size") String size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

}
