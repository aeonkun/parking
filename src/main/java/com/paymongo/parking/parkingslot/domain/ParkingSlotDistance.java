/**
 * 
 */
package com.paymongo.parking.parkingslot.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.paymongo.parking.entrypoint.domain.EntryPoint;

/**
 * Domain class for the {@link ParkingSlotDistance} entity
 *
 */
@Entity
@Table(name = "parking_slot_distance")
public class ParkingSlotDistance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "entry_point_id")
	private EntryPoint entryPoint;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parking_slot_id")
	private ParkingSlot parkingSlot;
	private int distance;

	public ParkingSlotDistance() {
	}

	/**
	 * Creates the {@link ParkingSlotDistance}
	 * 
	 * @param id          id of the {@link ParkingSlotDistance)
	 * @param entryPoint  {@link EntryPoint} of the {@link ParkingSlotDistance)
	 * @param parkingSlot {@link ParkingSlot} of the {@link ParkingSlotDistance)
	 * @param distance    distance of the {@link ParkingSlotDistance)
	 */
	public ParkingSlotDistance(long id, EntryPoint entryPoint, ParkingSlot parkingSlot, int distance) {
		this.id = id;
		this.entryPoint = entryPoint;
		this.parkingSlot = parkingSlot;
		this.distance = distance;
	}

	/**
	 * Gets the id
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the {@link EntryPoint}
	 * 
	 * @return {@link EntryPoint}
	 */
	public EntryPoint getEntryPoint() {
		return entryPoint;
	}

	/**
	 * Sets the {@link EntryPoint}
	 * 
	 * @param entryPoint
	 */
	public void setEntryPoint(EntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}

	/**
	 * Gets the {@link ParkingSlot}
	 * 
	 * @return {@link ParkingSlot}
	 */
	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}

	/**
	 * Sets the {@link ParkingSlot}
	 * 
	 * @param parkingSlot
	 */
	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}

	/**
	 * Gets the distance
	 * 
	 * @return distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Sets the distance
	 * 
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

}
