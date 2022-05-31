package com.paymongo.parking.entrypoint.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paymongo.parking.entrypoint.dao.EntryPointRepository;
import com.paymongo.parking.entrypoint.domain.EntryPoint;
import com.paymongo.parking.entrypoint.dto.EntryPointDto;
import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.parkingslot.dao.ParkingSlotDistanceRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntryPointService {

	private static Logger logger = LoggerFactory.getLogger(EntryPointService.class);

	@Autowired
	EntryPointRepository entryPointRepository;

	@Autowired
	ParkingSlotDistanceRepository parkingSlotDistanceRepository;

	/**
	 * Creates an {@link EntryPoint} with the given entry point data.
	 * 
	 * @param entryPointRequest the entry point data to be created.
	 * @return the {@link EntryPoint} that was created.
	 */
	private EntryPoint createEntryPoint(EntryPointDto entryPointDto) {

		EntryPoint entryPoint = new EntryPoint();
		entryPoint.setName(entryPointDto.getName());
		logger.info("id: {} name: {}", entryPoint.getId(), entryPoint.getName());

		return entryPointRepository.saveAndFlush(entryPoint);
	}

	public List<EntryPoint> createEntryPoints(List<EntryPointDto> entryPointDtos) {

		List<EntryPoint> entryPoints = new ArrayList<>();
		for (EntryPointDto entryPointDto : entryPointDtos) {
			entryPoints.add(createEntryPoint(entryPointDto));
		}

		return entryPoints;
	}

	/**
	 * Retrieves all {@link EntryPoint} from the database
	 *
	 * @return a list of all entry points from the database
	 */
	public List<EntryPoint> getAllEntryPoints() {
		return entryPointRepository.findAll();
	}

	public EntryPoint getEntryPointByName(String name) {
		Optional<EntryPoint> entryPoint = entryPointRepository.findByName(name);
		if (!entryPoint.isPresent()) {
			return null;
		}
		return entryPoint.get();
	}

	private void deleteEntryPoint(String name) throws EntryPointException {
		EntryPoint entryPoint = getEntryPointByName(name);

		if (entryPoint == null) {
			throw new EntryPointException(EntryPointException.ENTRY_POINT_NOT_FOUND_EXCEPTION);
		}

		entryPointRepository.delete(entryPoint);
	}

	public List<String> deleteEntryPoints(List<EntryPointDto> entryPointDtos) throws EntryPointException {
		List<String> deletedEntryPointNames = new ArrayList<String>();
		for (EntryPointDto entryPointDto : entryPointDtos) {
			parkingSlotDistanceRepository.deleteByEntryPointName(entryPointDto.getName());
			deleteEntryPoint(entryPointDto.getName());

			deletedEntryPointNames.add(entryPointDto.getName());
		}

		return deletedEntryPointNames;
	}

}
