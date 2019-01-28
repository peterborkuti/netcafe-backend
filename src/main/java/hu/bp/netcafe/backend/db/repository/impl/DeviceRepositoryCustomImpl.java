package hu.bp.netcafe.backend.db.repository.impl;

import hu.bp.netcafe.backend.db.entity.Device;
import hu.bp.netcafe.backend.db.repository.DeviceRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;

@Repository
@Transactional(readOnly=false)
public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {
  @PersistenceContext
  EntityManager entityManager;
  /**
   * Finds all devices which are consuming the network
   *
   * @return
   */
  @Override
  public Stream<Device> findAllOnNet() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Device> criteriaQuery = criteriaBuilder.createQuery(Device.class);

    Root<Device> device = criteriaQuery.from(Device.class);

    Predicate onNetPredicate = criteriaBuilder.isTrue(device.get("onNet"));

    criteriaQuery.where(onNetPredicate);

    TypedQuery<Device> query = entityManager.createQuery(criteriaQuery);

    return query.getResultStream();
  }
}
