package com.paymongo.parking.parkingcomplex.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.parkingcomplex.domain.VehicleParkingDetails;

/**
 * Repository for the {@link VehicleParkingDetails} entity
 *
 */
@Repository
public interface VehicleParkingDetailsRepository extends JpaRepository<VehicleParkingDetails, Long> {

	/**
	 * Finds the first entry from {@link VehicleParkingDetails} where plate number
	 * is equal to the provided plate number and where date_time_unparked is not
	 * null and order by date_time_unparked descending.
	 * 
	 * This returns previous vehicle parking details of the vehicle if present in
	 * the database.
	 * 
	 * @param plateNumber of the vehicle
	 * @return {@link Optional} {@link VehicleParkingDetails} the previous vehicle
	 *         parking details of the vehicle
	 */
	Optional<VehicleParkingDetails> findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc(
			String plateNumber);

	/**
	 * Finds the {@link VehicleParkingDetails} of the vehicle with the provided
	 * plate number with the corresponding date and time in which it was unparked.
	 * 
	 * @param plateNumber      of the vehicle
	 * @param dateTimeUnparked of the vehicle
	 * @return {@link Optional} {@link VehicleParkingDetails} with the provided
	 *         plate number and date time of exit
	 */
	Optional<VehicleParkingDetails> findByVehiclePlateNumberAndDateTimeUnparked(String plateNumber,
			LocalDateTime dateTimeUnparked);

}
