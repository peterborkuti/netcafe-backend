package hu.bp.netcafe.backend.db.job;

import hu.bp.netcafe.backend.db.repository.DeviceRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class UpdateRemainingTimeJobTest {
	@InjectMocks
	private UpdateRemainingTimeJob updateRemainingTimeJob;

	@Mock
	private DeviceRepository deviceRepository;

	@Test
	public void decreaseTimeInDevicesMethodShouldBeScheduled() throws NoSuchMethodException {
		Scheduled annotation = (Scheduled)
				UpdateRemainingTimeJob.class.
						getDeclaredMethod("decreaseTimeInDevices").
						getAnnotation(Scheduled.class);
		assertEquals(60000, annotation.fixedDelay());
	}

	@Test
	public void decreaseTimeInDevices_ShouldCall_decreaseRemainingTimeAllOnNet() {
		MockitoAnnotations.initMocks(this);

		updateRemainingTimeJob.decreaseTimeInDevices();

		verify(deviceRepository).decreaseRemainingTimeAllOnNet();
	}
}
