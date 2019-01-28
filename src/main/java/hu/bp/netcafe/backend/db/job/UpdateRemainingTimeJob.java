package hu.bp.netcafe.backend.db.job;

import hu.bp.netcafe.backend.db.entity.Device;
import hu.bp.netcafe.backend.db.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class UpdateRemainingTimeJob extends QuartzJobBean{
  @Autowired
  private DeviceRepository deviceRepository;

  @Transactional(readOnly=false)
  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    /**
     * I tried parallel(), but http://coopsoft.com/ar/Calamity2Article.html
     * I used deviceRepository.saveAll(), but for that, I had to collect devices first, which
     * eliminates the advantages of using a stream, so the option is forEach.
     */
      deviceRepository.findAllOnNet().
      map(Device::decrementRemainingTimeAndSetOnNetIfDeviceIsOnNet).
      forEach(deviceRepository::save);

    log.info("Job run");
  }
}
