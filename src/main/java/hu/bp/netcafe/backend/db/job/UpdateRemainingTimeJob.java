package hu.bp.netcafe.backend.db.job;

import hu.bp.netcafe.backend.db.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UpdateRemainingTimeJob {
  @Autowired
  private DeviceRepository deviceRepository;

  @Scheduled(fixedDelay = 2000)
  public void decreaseTimeInDevices() {
    int updated = deviceRepository.decreaseRemainingTimeAllOnNet();
  }
}
