package hu.bp.netcafe.backend.db;

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
  CommandLineRunner initFamily(FamilyRepository familyRepository) {
    return args -> {
      Family family1 = new Family("Family 1");
      log.info("Preloading " + familyRepository.save(family1));
      log.info("Preloading " + familyRepository.save(new Family("Family 2")));
    };
  }

  @Order(2)
  @Bean
  CommandLineRunner initMember(FamilyRepository familyRepository, MemberRepository memberRepository) {
    return args -> {
      Family family = familyRepository.findAll().get(0);

      Member user1 = new Member("Family 1 Admin", Role.PARENT);
      user1.setFamily(family);
      familyRepository.save(family);
      log.info("Preloading " + memberRepository.save(user1));

      Member user2 = new Member("Family 1 Child 1", Role.CHILD);
      user1.setFamily(family);
      Member user3 = new Member("Family 1 Child 2", Role.CHILD);
      user1.setFamily(family);

      log.info("Preloading " + memberRepository.save(user2));
      log.info("Preloading " + memberRepository.save(user3));
    };
  }

}
