package hu.bp.netcafe.backend.db.repository;

import hu.bp.netcafe.backend.db.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FamilyRepository extends JpaRepository<Family, UUID>{
}
