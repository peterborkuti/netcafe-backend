package hu.bp.netcafe.backend.db.repository.impl;

import hu.bp.netcafe.backend.db.entity.Device;
import hu.bp.netcafe.backend.db.repository.DeviceRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {
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
  public int decreaseRemainingTimeAllOnNet() {
      decreaseTime = entityManager.createQuery(
        "UPDATE Device AS d SET d.remainingTime = d.remainingTime - 1 " +
          "WHERE d.onNet = true AND d.remainingTime > 0");

      checkAndSetOnNet = entityManager.createQuery(
        "UPDATE Device AS d SET d.onNet = false " +
          "WHERE d.onNet = true AND d.remainingTime = 0");

    entityManager.flush();

    int updateCount = decreaseTime.executeUpdate();

    if (updateCount > 0) {
      checkAndSetOnNet.executeUpdate();

      entityManager.clear();
    }


    return updateCount;
  }
}
