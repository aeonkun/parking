package com.paymongo.parking.vehicle;

public class VehicleException extends Exception {
	public static final String VEHICLE_NOT_FOUND_EXCEPTION = "Vehicle not found";
	public static final String INVALID_VEHICLE_SIZE = "Invalid vehicle size!";

	/**
	 * 
	 */
	private static final long serialVersionUID = 166119867986237286L;

	public VehicleException(String message) {
		super(message);
	}

}
