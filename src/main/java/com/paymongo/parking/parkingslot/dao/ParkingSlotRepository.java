package com.paymongo.parking.parkingslot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.parkingslot.domain.ParkingSlot;

/**
 * Repository for the {@link ParkingSlot} entity
 *
 */
@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

	/**
	 * Find {@link ParkingSlot} by name.
	 * 
	 * @param name of the parking slot.
	 * @return {@link Optional} {@link ParkingSlot}.
	 */
	Optional<ParkingSlot> findByName(String name);
}
