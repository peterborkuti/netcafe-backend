package hu.bp.netcafe.backend.db.repository;

/**
 * Name is mandatory to be ${Original Repository name}Custom
 */
public interface DeviceRepositoryCustom {
  /**
   * Decreases the remainingTime on all devices which are consuming the network and
   * sets onNet when remainingTime is zero.
   * @return
   */
  public int decreaseRemainingTimeAllOnNet();
}
