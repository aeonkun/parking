package com.paymongo.parking.vehicle.dto;

import com.paymongo.parking.vehicle.domain.Vehicle;

/**
 * Data transfer object for the vehicle
 *
 */
public class VehicleDto {

	private final String plateNumber;
	private final String size;

	/**
	 * Creates {@link VehicleDto}
	 * 
	 * @param plateNumber plate number of the vehicle
	 * @param size        size of the vehicle
	 */
	public VehicleDto(String plateNumber, String size) {
		this.plateNumber = plateNumber;
		this.size = size;
	}

	/**
	 * Gets the plate number of the {@link Vehicle}
	 * 
	 * @return plate number
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * Gets the size of the {@link Vehicle}
	 * 
	 * @return size
	 */
	public String getSize() {
		return size;
	}

}
