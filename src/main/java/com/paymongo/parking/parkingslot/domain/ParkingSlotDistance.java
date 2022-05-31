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
 * @author carlo
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

	public ParkingSlotDistance(long id, EntryPoint entryPoint, ParkingSlot parkingSlot, int distance) {
		this.id = id;
		this.entryPoint = entryPoint;
		this.parkingSlot = parkingSlot;
		this.distance = distance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EntryPoint getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(EntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}

	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}

	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
