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

/**
 * Domain class for {@link VehicleParkingDetails} entity.
 *
 */
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

	/**
	 * Constructor for {@link VehicleParkingDetails}.
	 * 
	 * @param id               of the {@link VehicleParkingDetails}.
	 * @param parkingSlot      slot assigned to be parked on.
	 * @param vehicle          that is assigned to park.
	 * @param dateTimeParked   date and time when the vehicle parked.
	 * @param dateTimeUnparked date and time when the vehicle departed.
	 * @param parkingFee       total parking fee paid.
	 */
	public VehicleParkingDetails(long id, ParkingSlot parkingSlot, Vehicle vehicle, LocalDateTime dateTimeParked,
			LocalDateTime dateTimeUnparked, int parkingFee) {
		this.id = id;
		this.parkingSlot = parkingSlot;
		this.vehicle = vehicle;
		this.dateTimeParked = dateTimeParked;
		this.dateTimeUnparked = dateTimeUnparked;
		this.parkingFee = parkingFee;
	}

	/**
	 * Get id of the {@link VehicleParkingDetails}.
	 * 
	 * @return id of the {@link VehicleParkingDetails}.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set id of the {@link VehicleParkingDetails}.
	 * 
	 * @param id of the {@link VehicleParkingDetails}.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get {@link ParkingSlot} of the {@link VehicleParkingDetails}.
	 * 
	 * @return {@link ParkingSlot} of the {@link VehicleParkingDetails}.
	 */
	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}

	/**
	 * Set the {@link ParkingSlot} of the {@link VehicleParkingDetails}.
	 * 
	 * @param parkingSlot to be assigned.
	 */
	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}

	/**
	 * Get the vehicle of the {@link VehicleParkingDetails}.
	 * 
	 * @return {@link Vehicle} of the {@link VehicleParkingDetails}.
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * Set the vehicle of the {@link VehicleParkingDetails}.
	 * 
	 * @param vehicle to be assigned.
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * Gets the date and time of parking.
	 * 
	 * @return the date and time of parking.
	 */
	public LocalDateTime getDateTimeParked() {
		return dateTimeParked;
	}

	/**
	 * Sets the date and time of parking.
	 * 
	 * @param dateTimeParked time when the vehicle parked.
	 */
	public void setDateTimeParked(LocalDateTime dateTimeParked) {
		this.dateTimeParked = dateTimeParked;
	}

	/**
	 * Get the date and time of departure.
	 * 
	 * @return date and time of departure.
	 */
	public LocalDateTime getDateTimeUnparked() {
		return dateTimeUnparked;
	}

	/**
	 * Sets the date and time when the vehicle departed
	 * 
	 * @param dateTimeUnparked when the vehicle departed
	 */
	public void setDateTimeUnparked(LocalDateTime dateTimeUnparked) {
		this.dateTimeUnparked = dateTimeUnparked;
	}

	/**
	 * Gets the parking fee
	 * 
	 * @return the parking fee
	 */
	public int getParkingFee() {
		return parkingFee;
	}

	/**
	 * Sets the parking fee
	 * 
	 * @param parkingFee
	 */
	public void setParkingFee(int parkingFee) {
		this.parkingFee = parkingFee;
	}

}
