package hu.bp.netcafe.backend.db.repository.impl;

import hu.bp.netcafe.backend.db.repository.DeviceRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Repository
public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {
  private static final String DECREASE_TIME =
      "UPDATE Device AS d SET d.remainingTime = d.remainingTime - 1 " +
      "WHERE d.onNet = true AND d.remainingTime > 0";

  private static final String CHECK_AND_SET_ON_NET =
      "UPDATE Device AS d SET d.onNet = false " +
      "WHERE d.onNet = true AND d.remainingTime = 0";

  @PersistenceContext
  EntityManager entityManager;

  private Query decreaseTime;
  private Query checkAndSetOnNet;

  /**
   * Finds all devices which are consuming the network
   *
   * @return
   */
  @Override
  @Transactional
  public int decreaseRemainingTimeAllOnNet() {
    entityManager.flush();

    decreaseTime = entityManager.createQuery(DECREASE_TIME);

    int updateCount = decreaseTime.executeUpdate();

    if (updateCount > 0) {
      checkAndSetOnNet = entityManager.createQuery(CHECK_AND_SET_ON_NET);
      checkAndSetOnNet.executeUpdate();

      entityManager.clear();
    }

    return updateCount;
  }
}
