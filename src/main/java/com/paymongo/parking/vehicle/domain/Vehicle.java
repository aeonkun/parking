package com.paymongo.parking.vehicle.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Domain class for {@link Vehicle} entity
 *
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String plateNumber;
	private String size;

	public Vehicle() {
	}

	/**
	 * Creates {@link Vehicle}
	 * 
	 * @param id          id of the vehicle
	 * @param plateNumber plate number of the vehicle
	 * @param size        size of the vehicle
	 */
	public Vehicle(long id, String plateNumber, String size) {
		this.id = id;
		this.plateNumber = plateNumber;
		this.size = size;
	}

	/**
	 * Gets the id of the {@link Vehicle}
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of the {@link Vehicle}
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the plate number.
	 * 
	 * @return plate number of the vehicle
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * Sets the plate number of the {@link Vehicle}
	 * 
	 * @param plateNumber of the {@link Vehicle}
	 */
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	/**
	 * Gets the size of the vehicle.
	 * 
	 * @return size of the vehicle.
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the size of the vehicle.
	 * 
	 * @param size of the vehicle.
	 */
	public void setSize(String size) {
		this.size = size;
	}

}
