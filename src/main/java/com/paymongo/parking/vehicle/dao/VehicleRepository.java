package com.paymongo.parking.vehicle.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.vehicle.domain.Vehicle;

/**
 * Repository for {@link VehicleRepository} entity.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	/**
	 * Find {@link Vehicle} by plate number.
	 * 
	 * @param plateNumber plate number of the car.
	 * @return {@link Vehicle} found.
	 */
	Optional<Vehicle> findByPlateNumber(String plateNumber);
}
