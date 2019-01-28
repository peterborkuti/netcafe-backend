package hu.bp.netcafe.backend.db;

import hu.bp.netcafe.backend.db.entity.Device;
import hu.bp.netcafe.backend.db.entity.Family;
import hu.bp.netcafe.backend.db.entity.Role;
import hu.bp.netcafe.backend.db.entity.Member;
import hu.bp.netcafe.backend.db.repository.FamilyRepository;
import hu.bp.netcafe.backend.db.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
public class LoadDatabase {
  @Order(1)
  @Bean
  CommandLineRunner initFamily(FamilyRepository familyRepository, MemberRepository memberRepository) {
    return args -> {
      fillDB(familyRepository, memberRepository);
    };
  }

  private static void fillDB(FamilyRepository familyRepository, MemberRepository memberRepository) {
    Member user1 = new Member("Family 1 Admin", Role.PARENT);
    Member user2 = new Member("Family 1 Child", Role.CHILD);
    Family family = new Family("Family 1");
    Device device = new Device("Laptop", "00:10:20:30:40:50");

    user1.addDevice(device);
    family.addMember(user1);

    familyRepository.save(family);

    family = familyRepository.findAll().get(0);
    family.addMember(user2);
    familyRepository.save(family);
  }

}
