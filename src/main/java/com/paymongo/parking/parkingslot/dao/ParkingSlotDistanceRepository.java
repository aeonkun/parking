package com.paymongo.parking.parkingslot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.entrypoint.domain.EntryPoint;
import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.parkingslot.domain.ParkingSlotDistance;

/**
 * Repository for {@link ParkingSlotDistance} entity
 *
 */
@Repository
public interface ParkingSlotDistanceRepository extends JpaRepository<ParkingSlotDistance, Long> {

	/**
	 * Finds all {@link ParkingSlotDistance} by entry point from the database
	 * 
	 * @param entryPoint to be used as filter.
	 * @return list of {@link ParkingSlotDistance} from the database
	 */
	List<ParkingSlotDistance> findAllByEntryPoint(EntryPoint entryPoint);

	/**
	 * Find nearest vacant {@link ParkingSlotDistance} that contains the provided
	 * sizes.
	 * 
	 * @param entryPoint entry point to be used as filter
	 * @param size       list of acceptable parking slot sizes
	 * @return {@link Optional} {@link ParkingSlotDistance}
	 */
	Optional<ParkingSlotDistance> findFirstByEntryPointAndParkingSlotSizeInAndParkingSlotIsVacantTrueOrderByDistanceAsc(
			EntryPoint entryPoint, List<String> size);

	Optional<ParkingSlotDistance> findByEntryPointAndParkingSlot(EntryPoint entryPoint, ParkingSlot parkingSlot);

}
