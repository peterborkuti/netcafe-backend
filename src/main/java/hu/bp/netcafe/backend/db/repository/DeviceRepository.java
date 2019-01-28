package hu.bp.netcafe.backend.db.repository;

import hu.bp.netcafe.backend.db.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID>, DeviceRepositoryCustom {
}
