package com.paymongo.parking.parkingcomplex.exception;

public class VehicleParkingDetailsException extends Exception {
	public static final String VEHICLE_PARKING_DETAILS_NOT_FOUND_EXCEPTION = "Vehicle parking details not found.";
	public static final String NO_ACTIVE_VEHICLE_PARKING_DETAILS_EXCEPTION = "No active vehicle parking details.";
	public static final String VEHICLE_IS_ALREADY_PARKED_EXCEPTION = "Vehicle is already parked";
	public static final String AN_ERROR_OCCURED_WHILE_PROCESSING_VEHICLE_PARKING_DETAILS = "An error has occured while processing vehicle parking details";
	public static final String INVALID_DATE = "Invalid Date";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3226185138780960643L;

	public VehicleParkingDetailsException(String message) {
		super(message);
	}

}
