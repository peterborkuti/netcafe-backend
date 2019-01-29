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

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    int updated = deviceRepository.decreaseRemainingTimeAllOnNet();
  }
}
