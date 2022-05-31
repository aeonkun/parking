package com.paymongo.parking.parkingslot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.entrypoint.domain.EntryPoint;
import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.parkingslot.domain.ParkingSlotDistance;

@Repository
public interface ParkingSlotDistanceRepository extends JpaRepository<ParkingSlotDistance, Long> {

	List<ParkingSlotDistance> findAllByEntryPoint(EntryPoint entryPoint);

	List<ParkingSlotDistance> findByEntryPointAndParkingSlotIsVacantTrueOrderByDistanceAsc(EntryPoint entryPoint);

	List<ParkingSlotDistance> findByEntryPointAndParkingSlotSizeInAndParkingSlotIsVacantTrueOrderByDistanceAsc(
			EntryPoint entryPoint, List<String> size);

	Optional<ParkingSlotDistance> findFirstByEntryPointAndParkingSlotSizeInAndParkingSlotIsVacantTrueOrderByDistanceAsc(
			EntryPoint entryPoint, List<String> size);

	Optional<ParkingSlotDistance> findByEntryPointAndParkingSlot(EntryPoint entryPoint, ParkingSlot parkingSlot);

	void deleteByEntryPointName(String entryPointName);

}
