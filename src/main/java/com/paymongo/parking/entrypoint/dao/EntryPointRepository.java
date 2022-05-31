package com.paymongo.parking.entrypoint.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.entrypoint.domain.EntryPoint;

@Repository
public interface EntryPointRepository extends JpaRepository<EntryPoint, Long> {

	Optional<EntryPoint> findByName(String name);
}
