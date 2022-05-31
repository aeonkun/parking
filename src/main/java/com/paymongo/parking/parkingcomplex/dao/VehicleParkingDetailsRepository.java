package com.paymongo.parking.parkingcomplex.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.parkingcomplex.domain.VehicleParkingDetails;

@Repository
public interface VehicleParkingDetailsRepository extends JpaRepository<VehicleParkingDetails, Long> {
	Optional<VehicleParkingDetails> findFirstByVehicleIdAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc(
			long vehicleId);

	Optional<VehicleParkingDetails> findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc(
			String plateNumber);

	Optional<VehicleParkingDetails> findByVehicleIdAndDateTimeUnparked(long vehicleId, LocalDateTime dateTimeUnparked);

	Optional<VehicleParkingDetails> findByVehiclePlateNumberAndDateTimeUnparked(String plateNumber,
			LocalDateTime dateTimeUnparked);

}
