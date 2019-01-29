package hu.bp.netcafe.backend.db.job;

import hu.bp.netcafe.backend.db.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class UpdateRemainingTimeJob {
  @Autowired
  private DeviceRepository deviceRepository;

  @Scheduled(fixedDelay = 60000)
  public void decreaseTimeInDevices() {
    deviceRepository.decreaseRemainingTimeAllOnNet();
  }
}
