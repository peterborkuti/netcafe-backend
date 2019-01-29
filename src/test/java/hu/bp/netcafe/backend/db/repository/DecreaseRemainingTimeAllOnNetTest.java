package hu.bp.netcafe.backend.db.repository;

import hu.bp.netcafe.backend.db.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
@TestPropertySource(
		locations = "classpath:application-test.properties")
public class DecreaseRemainingTimeAllOnNetTest {
	@Autowired
	DeviceRepository deviceRepository;

	@Test
	public void shouldDecreaseTimeWhenOnNet() {
		Device dev = new Device();
		dev.setOnNet(true);
		dev.setRemainingTime(2);

		deviceRepository.save(dev);

		deviceRepository.decreaseRemainingTimeAllOnNet();

		Device read = deviceRepository.getOne(dev.getId());

		assertEquals(1, read.getRemainingTime());
		assertTrue(read.isOnNet());
	}

	public void shouldNotDecreaseTimeWhenNotOnNet() {
		Device dev = new Device();
		dev.setOnNet(false);
		dev.setRemainingTime(2);

		deviceRepository.save(dev);

		deviceRepository.decreaseRemainingTimeAllOnNet();

		dev = deviceRepository.getOne(dev.getId());

		assertEquals(2, dev.getRemainingTime());
		assertFalse(dev.isOnNet());
	}

	public void shouldSetOnNetWhenRemainingTimeIsZeroAndDeviceWasOnNet() {
		Device dev = new Device();
		dev.setOnNet(true);
		dev.setRemainingTime(1);

		deviceRepository.save(dev);

		deviceRepository.decreaseRemainingTimeAllOnNet();

		dev = deviceRepository.getOne(dev.getId());

		assertEquals(0, dev.getRemainingTime());
		assertFalse(dev.isOnNet());
	}

	public void shouldSetOnNetAndDontGoBelowZeroRemainigTimeWhenRemainingTimeIsZeroAndDeviceWasOnNet() {
		Device dev = new Device();
		dev.setOnNet(true);
		dev.setRemainingTime(0);

		deviceRepository.save(dev);

		deviceRepository.decreaseRemainingTimeAllOnNet();

		dev = deviceRepository.getOne(dev.getId());

		assertEquals(0, dev.getRemainingTime());
		assertFalse(dev.isOnNet());
	}
}
