package com.paymongo.parking.parkingcomplex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.paymongo.parking.entrypoint.domain.EntryPoint;
import com.paymongo.parking.entrypoint.exception.EntryPointException;
import com.paymongo.parking.entrypoint.service.EntryPointService;
import com.paymongo.parking.parkingcomplex.dao.VehicleParkingDetailsRepository;
import com.paymongo.parking.parkingcomplex.domain.VehicleParkingDetails;
import com.paymongo.parking.parkingcomplex.dto.ParkRequestDto;
import com.paymongo.parking.parkingcomplex.dto.UnparkRequestDto;
import com.paymongo.parking.parkingcomplex.exception.VehicleParkingDetailsException;
import com.paymongo.parking.parkingcomplex.service.ParkingService;
import com.paymongo.parking.parkingslot.dao.ParkingSlotDistanceRepository;
import com.paymongo.parking.parkingslot.domain.ParkingSlot;
import com.paymongo.parking.parkingslot.enums.ParkingSlotEnum;
import com.paymongo.parking.parkingslot.exception.ParkingSlotException;
import com.paymongo.parking.parkingslot.service.ParkingSlotService;
import com.paymongo.parking.vehicle.VehicleException;
import com.paymongo.parking.vehicle.domain.Vehicle;
import com.paymongo.parking.vehicle.dto.VehicleDto;
import com.paymongo.parking.vehicle.enums.VehicleSizeEnum;
import com.paymongo.parking.vehicle.service.VehicleService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ParkingServiceTest {

	@Mock
	ParkingSlotDistanceRepository parkingSlotDistanceRepositoryMock;

	@Mock
	VehicleParkingDetailsRepository vehicleParkingDetailsRepositoryMock;

	@Mock
	VehicleService vehicleServiceMock;

	@Mock
	EntryPointService entryPointServiceMock;

	@Mock
	ParkingSlotService parkingSlotServiceMock;

	@InjectMocks
	ParkingService parkingService;

	@Test
	void testParkVehicleValid()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		ParkRequestDto parkRequestDto = new ParkRequestDto("A", new VehicleDto("AAA123", "L"),
				LocalDateTime.parse("2022-05-30T03:00:00"));

		EntryPoint entryPoint = new EntryPoint();
		entryPoint.setId(1L);
		entryPoint.setName("A");

		VehicleParkingDetails vehicleParkingDetails = new VehicleParkingDetails();
		vehicleParkingDetails.setId(1L);
		vehicleParkingDetails.setParkingFee(0);

		ParkingSlot parkingSlot = new ParkingSlot();
		parkingSlot.setId(1L);
		parkingSlot.setName("S1");
		parkingSlot.setSize("L");
		parkingSlot.setVacant(false);

		Vehicle vehicle = new Vehicle();
		vehicle.setId(1L);
		vehicle.setPlateNumber("AAA123");
		vehicle.setSize("L");

		vehicleParkingDetails.setParkingSlot(parkingSlot);
		vehicleParkingDetails.setVehicle(vehicle);
		vehicleParkingDetails.setDateTimeParked(LocalDateTime.parse("2022-05-30T00:00:00"));

		ParkingSlot parkingSlotVacant = new ParkingSlot();
		parkingSlotVacant.setId(1L);
		parkingSlotVacant.setName("S1");
		parkingSlotVacant.setSize("L");
		parkingSlotVacant.setVacant(true);

		when(vehicleServiceMock.createVehicle(Mockito.any(VehicleDto.class))).thenReturn(vehicle);
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked(Mockito.anyString(),
				Mockito.any())).thenReturn(Optional.empty());
		when(parkingSlotServiceMock.assignParkingSlot(entryPoint.getName(), vehicle.getSize()))
				.thenReturn(parkingSlotVacant);
		when(vehicleParkingDetailsRepositoryMock.save(Mockito.any(VehicleParkingDetails.class)))
				.thenReturn(vehicleParkingDetails);

		VehicleParkingDetails parkedVehicleDetail = parkingService.parkVehicle(parkRequestDto);

		verify(vehicleServiceMock, times(1)).createVehicle(Mockito.any(VehicleDto.class));
		verify(vehicleParkingDetailsRepositoryMock, times(1))
				.findByVehiclePlateNumberAndDateTimeUnparked(Mockito.anyString(), Mockito.any());
		verify(vehicleParkingDetailsRepositoryMock, times(1)).save(Mockito.any(VehicleParkingDetails.class));

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-30T00:00:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isZero();

	}

	@Test
	void testProcessVehicleUnparkRequestMinimum()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-30T03:00:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-30T00:00:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);
		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-30T00:00:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(40);

	}

	@Test
	void testProcessVehicleUnparkRequestMoreThan3LessThan24()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-30T08:00:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-30T00:00:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));
		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-30T00:00:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(540);

	}

	@Test
	void testProcessVehicleUnparkRequest24Hours()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-21T00:00:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T00:00:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-20T00:00:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(5000);

	}

	@Test
	void testProcessVehicleUnparkRequestMoreThan24WithExcess()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-21T02:00:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T00:00:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-20T00:00:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(5200);

	}

	@Test
	void testProcessVehicleUnparkRequestContinuousMinimum()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-20T03:00:00"));

		VehicleParkingDetails prevVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T00:00:00"),
				LocalDateTime.parse("2022-05-20T01:00:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T02:00:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));
		when(vehicleParkingDetailsRepositoryMock
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123"))
						.thenReturn(Optional.of(prevVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);
		verify(vehicleParkingDetailsRepositoryMock, times(1))
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123");

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-20T02:00:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isZero();

	}

	@Test
	void testProcessVehicleUnparkRequestContinuousMoreThan3LessThan24()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-20T05:00:00"));

		VehicleParkingDetails prevVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T00:00:00"),
				LocalDateTime.parse("2022-05-20T01:30:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T02:30:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));
		when(vehicleParkingDetailsRepositoryMock
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123"))
						.thenReturn(Optional.of(prevVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);
		verify(vehicleParkingDetailsRepositoryMock, times(1))
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123");

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-20T02:30:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(200);

	}

	@Test
	void testProcessVehicleUnparkRequestContinuousMoreThan24()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-22T00:00:00"));

		VehicleParkingDetails prevVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T00:00:00"),
				LocalDateTime.parse("2022-05-20T01:30:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T02:30:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));
		when(vehicleParkingDetailsRepositoryMock
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123"))
						.thenReturn(Optional.of(prevVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);
		verify(vehicleParkingDetailsRepositoryMock, times(1))
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123");

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-20T02:30:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(10000);

	}

	@Test
	void testProcessVehicleUnparkRequestContinuousMoreThan24WithExcess()
			throws ParkingSlotException, VehicleParkingDetailsException, EntryPointException, VehicleException {

		UnparkRequestDto unparkRequestDto = new UnparkRequestDto("AAA123", LocalDateTime.parse("2022-05-22T02:00:00"));

		VehicleParkingDetails prevVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T00:00:00"),
				LocalDateTime.parse("2022-05-20T01:30:00"));

		VehicleParkingDetails activeVehicleParkingDetails = createVehicleParkingDetails(VehicleSizeEnum.LARGE.getSize(),
				ParkingSlotEnum.LARGE.getSize(), LocalDateTime.parse("2022-05-20T02:30:00"), null);

		when(vehicleServiceMock.getVehicleByPlateNumber("AAA123")).thenReturn(activeVehicleParkingDetails.getVehicle());
		when(vehicleParkingDetailsRepositoryMock.findByVehiclePlateNumberAndDateTimeUnparked("AAA123", null))
				.thenReturn(Optional.of(activeVehicleParkingDetails));
		when(vehicleParkingDetailsRepositoryMock
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123"))
						.thenReturn(Optional.of(prevVehicleParkingDetails));

		VehicleParkingDetails parkedVehicleDetail = parkingService.processVehicleUnparkRequest(unparkRequestDto);

		verify(vehicleServiceMock, times(1)).getVehicleByPlateNumber("AAA123");
		verify(vehicleParkingDetailsRepositoryMock, times(1)).findByVehiclePlateNumberAndDateTimeUnparked("AAA123",
				null);
		verify(vehicleParkingDetailsRepositoryMock, times(1))
				.findFirstByVehiclePlateNumberAndDateTimeUnparkedNotNullOrderByDateTimeUnparkedDesc("AAA123");

		assertThat(parkedVehicleDetail.getVehicle().getPlateNumber()).isEqualTo("AAA123");
		assertThat(parkedVehicleDetail.getParkingSlot().getName()).isEqualTo("S1");
		assertThat(parkedVehicleDetail.getDateTimeParked()).isEqualTo(LocalDateTime.parse("2022-05-20T02:30:00"));
		assertThat(parkedVehicleDetail.getParkingFee()).isEqualTo(10200);

	}

	private VehicleParkingDetails createVehicleParkingDetails(String vehicleSize, String parkingSlotSize,
			LocalDateTime dateTimeParked, LocalDateTime dateTimeUnparked) {
		VehicleParkingDetails vehicleParkingDetails = new VehicleParkingDetails();
		vehicleParkingDetails.setId(1L);
		vehicleParkingDetails.setParkingFee(0);

		ParkingSlot parkingSlot = new ParkingSlot();
		parkingSlot.setId(1L);
		parkingSlot.setName("S1");
		parkingSlot.setSize(parkingSlotSize);
		parkingSlot.setVacant(false);

		Vehicle vehicle = new Vehicle();
		vehicle.setId(1L);
		vehicle.setPlateNumber("AAA123");
		vehicle.setSize(vehicleSize);

		vehicleParkingDetails.setParkingSlot(parkingSlot);
		vehicleParkingDetails.setVehicle(vehicle);
		vehicleParkingDetails.setDateTimeParked(dateTimeParked);
		vehicleParkingDetails.setParkingFee(0);
		vehicleParkingDetails.setDateTimeUnparked(dateTimeUnparked);

		return vehicleParkingDetails;
	}

}
