package com.paymongo.parking.entrypoint.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymongo.parking.entrypoint.domain.EntryPoint;

/**
 * Repository for the {@link EntryPoint} entity
 *
 */
@Repository
public interface EntryPointRepository extends JpaRepository<EntryPoint, Long> {

	/**
	 * Finds the {@link EntryPoint} with the provided name
	 * 
	 * @param name of the {@link EntryPoint} to be retrieved.
	 * @return An {@link Optional} {@link EntryPoint} with the provided name.
	 */
	Optional<EntryPoint> findByName(String name);
}
