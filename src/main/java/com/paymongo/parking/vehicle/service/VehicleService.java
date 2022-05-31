package com.paymongo.parking.vehicle.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymongo.parking.vehicle.dao.VehicleRepository;
import com.paymongo.parking.vehicle.domain.Vehicle;
import com.paymongo.parking.vehicle.dto.VehicleDto;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;

	/**
	 * Creates and saves {@link Vehicle} to the database.
	 * 
	 * @param vehicleDto the vehicle data to be saved
	 * @return {@link Vehicle} that was created.
	 *
	 */
	public Vehicle createVehicle(VehicleDto vehicleDto) {
		Vehicle vehicle = getVehicleByPlateNumber(vehicleDto.getPlateNumber());
		if (vehicle == null) {
			vehicle = new Vehicle();
			vehicle.setPlateNumber(vehicleDto.getPlateNumber());
			vehicle.setSize(vehicleDto.getSize());
			return vehicleRepository.saveAndFlush(vehicle);
		}

		return vehicle;

	}

	public Vehicle getVehicleByPlateNumber(String plateNumber) {
		Optional<Vehicle> vehicle = vehicleRepository.findByPlateNumber(plateNumber);
		if (!vehicle.isPresent()) {
			return null;
		}
		return vehicle.get();

	}

}
