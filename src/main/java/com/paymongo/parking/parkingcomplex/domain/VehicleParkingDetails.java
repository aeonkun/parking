package com.paymongo.parking.parkingcomplex.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.vehicle.domain.Vehicle;

@Entity
@Table(name = "vehicle_parking_details")
public class VehicleParkingDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parking_slot_id")
	private ParkingSlot parkingSlot;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	private LocalDateTime dateTimeParked;

	private LocalDateTime dateTimeUnparked;

	private int parkingFee;

	public VehicleParkingDetails() {
	}

	public VehicleParkingDetails(long id, ParkingSlot parkingSlot, Vehicle vehicle, LocalDateTime dateTimeParked,
			LocalDateTime dateTimeUnparked, int parkingFee) {
		this.id = id;
		this.parkingSlot = parkingSlot;
		this.vehicle = vehicle;
		this.dateTimeParked = dateTimeParked;
		this.dateTimeUnparked = dateTimeUnparked;
		this.parkingFee = parkingFee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}

	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public LocalDateTime getDateTimeParked() {
		return dateTimeParked;
	}

	public void setDateTimeParked(LocalDateTime dateTimeParked) {
		this.dateTimeParked = dateTimeParked;
	}

	public LocalDateTime getDateTimeUnparked() {
		return dateTimeUnparked;
	}

	public void setDateTimeUnparked(LocalDateTime dateTimeUnparked) {
		this.dateTimeUnparked = dateTimeUnparked;
	}

	public int getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(int parkingFee) {
		this.parkingFee = parkingFee;
	}

}
