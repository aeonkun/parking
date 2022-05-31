package com.paymongo.parking.parkingslot.exception;

public class ParkingSlotException extends Exception {

	public static final String PARKING_SLOT_NOT_FOUND_EXCEPTION = "Parking slot not found";
	public static final String PARKING_SLOT_DISTANCE_NOT_FOUND_EXCEPTION = "Parking slot distance not found";
	public static final String NO_AVAILABE_PARKING_SLOT_EXCEPTION = "No available parking slots found";

	/**
	 * 
	 */
	private static final long serialVersionUID = -6373447233458658974L;

	public ParkingSlotException(String message) {
		super(message);
	}
}
