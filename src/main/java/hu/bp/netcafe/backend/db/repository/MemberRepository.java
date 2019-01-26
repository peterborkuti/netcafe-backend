package hu.bp.netcafe.backend.db.repository;

import hu.bp.netcafe.backend.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID>{
}
