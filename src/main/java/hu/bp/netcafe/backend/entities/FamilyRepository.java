package hu.bp.netcafe.backend.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface FamilyRepository extends JpaRepository<Family, UUID>{
}
