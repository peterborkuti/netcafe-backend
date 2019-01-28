package hu.bp.netcafe.backend.db.repository;

import hu.bp.netcafe.backend.db.entity.Device;

import java.util.List;
import java.util.stream.Stream;

/**
 * Name is mandatory to be ${Original Repository name}Custom
 */
public interface DeviceRepositoryCustom {
  /**
   * Finds all devices which are consuming the network
   * @return
   */
  public Stream<Device> findAllOnNet();
}
