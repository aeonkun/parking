package com.paymongo.parking.parkingslot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@NamedQuery(name = "ParkingSlot.findByEntryPointAndSizeIn", query = "SELECT ps FROM ParkingSlot ps WHERE ps.entryPoints = ?1 AND ps.size IN ?2")
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

	public ParkingSlot(long id, String name, String size, boolean isVacant) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.isVacant = isVacant;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isVacant() {
		return isVacant;
	}

	public void setVacant(boolean isVacant) {
		this.isVacant = isVacant;
	}

}
