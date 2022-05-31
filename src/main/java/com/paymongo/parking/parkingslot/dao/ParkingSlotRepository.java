package com.paymongo.parking.parkingslot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.parkingslot.domain.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

	Optional<ParkingSlot> findByName(String name);
}
