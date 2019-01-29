package hu.bp.netcafe.backend.db.repository;

import hu.bp.netcafe.backend.db.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@PersistenceContext
	EntityManager entityManager;

	private void setup(int remainingTime, boolean onNet) {
		Device dev = new Device();
		dev.setRemainingTime(remainingTime);
		dev.setOnNet(onNet);

		deviceRepository.saveAndFlush(dev);
	}

	private void runFunctionToTest(int expectedCount) {
		int count = deviceRepository.decreaseRemainingTimeAllOnNet();

		assertEquals(expectedCount, count);
	}

	@Test
	public void shouldDecreaseTimeWhenOnNet() {
		setup(2, true);

		runFunctionToTest(1);

		Device read = deviceRepository.findAll().get(0);

		assertEquals(1, read.getRemainingTime());
		assertTrue(read.isOnNet());
	}

	@Test
	public void shouldNotDecreaseTimeWhenNotOnNet() {
		setup(2, false);

		runFunctionToTest(0);

		Device dev = deviceRepository.findAll().get(0);

		assertEquals(2, dev.getRemainingTime());
		assertFalse(dev.isOnNet());
	}

	@Test
	public void shouldSetOnNetWhenRemainingTimeIsZeroAndDeviceWasOnNet() {
		setup(1, true);

		runFunctionToTest(1);

		Device dev = deviceRepository.findAll().get(0);

		assertEquals(0, dev.getRemainingTime());
		assertFalse(dev.isOnNet());
	}

	/**
	 * zero-check update runs only in case there was at least 1 device to decrease time.
	 */
	@Test
	public void shouldSetOnNetAndDontGoBelowZeroRemainigTimeWhenRemainingTimeIsZeroAndDeviceWasOnNet() {
		setup(1, true); // Hack for letting the zero-check run
		setup(0, true);

		runFunctionToTest(1);

		deviceRepository.findAll().forEach(dev -> {
			assertEquals(0, dev.getRemainingTime());
			assertFalse(dev.isOnNet());
		});
	}
}
