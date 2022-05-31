package com.paymongo.parking.vehicle.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VehicleParkingInformation {

	private Vehicle vehicle;
	private boolean isParked;
	private LocalDateTime lastDateTimeParked;
	private LocalDate lastDateTimeUnparked;

	public VehicleParkingInformation() {
	}

	public VehicleParkingInformation(Vehicle vehicle, boolean isParked, LocalDateTime lastDateTimeParked,
			LocalDate lastDateTimeUnparked) {
		this.vehicle = vehicle;
		this.isParked = isParked;
		this.lastDateTimeParked = lastDateTimeParked;
		this.lastDateTimeUnparked = lastDateTimeUnparked;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public boolean isParked() {
		return isParked;
	}

	public void setParked(boolean isParked) {
		this.isParked = isParked;
	}

	public LocalDateTime getLastDateTimeParked() {
		return lastDateTimeParked;
	}

	public void setLastDateTimeParked(LocalDateTime lastDateTimeParked) {
		this.lastDateTimeParked = lastDateTimeParked;
	}

	public LocalDate getLastDateTimeUnparked() {
		return lastDateTimeUnparked;
	}

	public void setLastDateTimeUnparked(LocalDate lastDateTimeUnparked) {
		this.lastDateTimeUnparked = lastDateTimeUnparked;
	}

}
