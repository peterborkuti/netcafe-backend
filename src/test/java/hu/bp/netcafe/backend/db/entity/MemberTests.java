package hu.bp.netcafe.backend.db.entity;

import hu.bp.netcafe.backend.db.repository.DeviceRepository;
import hu.bp.netcafe.backend.db.repository.FamilyRepository;
import hu.bp.netcafe.backend.db.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
@TestPropertySource(
  locations = "classpath:application-test.properties")
public class MemberTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @Autowired
  private FamilyRepository familyRepository;

  @Test
  public void deviceShouldBeSaved() {
    String name = "Name";
    String mac = "00:00:00:00:00:00";
    Device dev = new Device(name, mac);
    deviceRepository.save(dev);

    assertEquals(name, deviceRepository.findAll().get(0).getName());
    assertEquals(mac, deviceRepository.findAll().get(0).getMacAddress());
  }

  @Test
  public void devicesShouldBeSavedAndListedThroughMember() {
    Member member = new Member("Member", Role.PARENT);
    Device dev1 = new Device("Dev1", "");
    Device dev2 = new Device("Dev2", "");

    member.addDevice(dev1);
    member.addDevice(dev2);

    memberRepository.save(member);

    List<Device> devices = deviceRepository.findAll();
    assertEquals(2, devices.size());
    assertEquals("Dev1", devices.get(0).getName());
    assertEquals("Dev2", devices.get(1).getName());
  }

  @Test
  public void devicesInAFamilyShouldBeSavedAndListed() {
    Family family1 = new Family("Family1");
    Family family2 = new Family("Family2");

    Member member1 = new Member("Member", Role.PARENT);
    Member member2 = new Member("Member1", Role.PARENT);
    Member member3 = new Member("Member2", Role.PARENT);

    Device dev1 = new Device("Dev1", "");
    Device dev2 = new Device("Dev2", "");
    Device dev3 = new Device("Dev3", "");
    Device dev4 = new Device("Dev4", "");

    family1.addMember(member1);
    family1.addMember(member2);

    family2.addMember(member3);

    member1.addDevice(dev1);
    member1.addDevice(dev2);
    member2.addDevice(dev3);
    member3.addDevice(dev4);

    familyRepository.save(family1);
    familyRepository.save(family2);

    Optional<Device> device = deviceRepository.findById(dev1.getId());
    assertTrue(device.isPresent());
    Optional<Member> owner = Optional.ofNullable(device.get().getOwner());
    assertTrue(owner.isPresent());
    Optional<Family> family = Optional.ofNullable(owner.get().getFamily());
    assertTrue(family.isPresent());

    List<Member> members = family.get().getMembers();
    List<Device> devices = members.stream().flatMap(member -> member.getDevices().stream()).collect(Collectors.toList());

    assertEquals(3, devices.size());
    assertTrue(devices.containsAll(devices));
  }
}
