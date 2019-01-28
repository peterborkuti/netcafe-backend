package hu.bp.netcafe.backend.db.entity;

import hu.bp.netcafe.backend.db.repository.FamilyRepository;
import hu.bp.netcafe.backend.db.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
@TestPropertySource(
  locations = "classpath:application-test.properties")
public class FamilyTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private FamilyRepository familyRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void familyShouldBeSaved() {
    String name = "FamilyName";
    Family family = new Family(name);
    familyRepository.save(family);
    assertEquals(name, familyRepository.findAll().get(0).getName());
  }

  public void membersShouldBeListedInFamily() {
    String name = "FamilyName";
    Family family = new Family(name);

    Member member1 = new Member("Member", Role.PARENT);
    Member member2 = new Member("Member1", Role.PARENT);

    family.getMembers().add(member1);
    family.getMembers().add(member2);

    familyRepository.save(family);

    Family read = familyRepository.findAll().get(0);
    assertEquals(2, read.getMembers().size());
    assertEquals("Member", read.getMembers().get(0).getName());
    assertEquals("Member1", read.getMembers().get(1).getName());
  }

  @Test
  public void membersShouldBeSavedThroughFamily() {
    String name = "FamilyName";
    Family family = new Family(name);

    Member member1 = new Member("Member", Role.PARENT);
    Member member2 = new Member("Member1", Role.PARENT);

    family.addMember(member1);
    family.addMember(member2);

    familyRepository.save(family);

    List<Member> members = memberRepository.findAll();
    assertEquals(2, members.size());
    assertEquals("Member", members.get(0).getName());
    assertEquals("Member1", members.get(1).getName());
  }

  @Test
  public void canListFamilyMembersBasedOnAMember() {
    Family family1 = new Family("Family1");
    Family family2 = new Family("Family2");

    Member member1 = new Member("Member", Role.PARENT);
    Member member2 = new Member("Member1", Role.PARENT);
    Member member3 = new Member("Member2", Role.PARENT);

    family1.addMember(member1);
    family1.addMember(member2);

    family2.addMember(member3);

    familyRepository.save(family1);
    familyRepository.save(family2);

    Optional<Member> member = memberRepository.findById(member1.getId());
    assertTrue(member.isPresent());
    Optional<Family> f = Optional.ofNullable(member.get().getFamily());
    assertTrue(f.isPresent());
    Optional<List<Member>> members = Optional.ofNullable(f.get().getMembers());
    assertTrue(members.isPresent());
    assertEquals(2, members.get().size());
    assertEquals("Member", members.get().get(0).getName());
    assertEquals("Member1", members.get().get(1).getName());
  }
}
