package com.paymongo.parking.parkingslot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain for the {@link ParkingSlot} entity
 *
 */
@Entity
public class ParkingSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String size;

	@Column(name = "is_vacant")
	private boolean isVacant;

	public ParkingSlot() {
	}

	/**
	 * Creates the {@link ParkingSlot}
	 * 
	 * @param id       id of the {@link ParkingSlot}
	 * @param name     name of the {@link ParkingSlot}
	 * @param size     size of the {@link ParkingSlot}
	 * @param isVacant vacancy status of the {@link ParkingSlot}
	 */
	public ParkingSlot(long id, String name, String size, boolean isVacant) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.isVacant = isVacant;
	}

	/**
	 * Gets the id of the {@link ParkingSlot}
	 * 
	 * @return id of the {@link ParkingSlot}
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the id of the {@link ParkingSlot}
	 * 
	 * @param id of the {@link ParkingSlot}
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name of the {@link ParkingSlot}
	 * 
	 * @return name of the {@link ParkingSlot}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the {@link ParkingSlot}
	 * 
	 * @param name of the {@link ParkingSlot}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get size of the {@link ParkingSlot}
	 * 
	 * @return size of the {@link ParkingSlot}
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the size of the {@link ParkingSlot}
	 * 
	 * @param size of the {@link ParkingSlot}
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Gets the vacancy status of {@link ParkingSlot}
	 * 
	 * @return vacancy status of {@link ParkingSlot}
	 */
	public boolean isVacant() {
		return isVacant;
	}

	/**
	 * Sets the vacancy status of {@link ParkingSlot}
	 * 
	 * @param isVacant vacancy status of the {@link ParkingSlot}
	 */
	public void setVacant(boolean isVacant) {
		this.isVacant = isVacant;
	}

}
