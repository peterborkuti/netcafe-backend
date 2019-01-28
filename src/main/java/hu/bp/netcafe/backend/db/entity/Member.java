package hu.bp.netcafe.backend.db.entity;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Slf4j
public class Member {
  @Id
  @Column( columnDefinition = "uuid", updatable = false )
  private UUID id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Role role;

  /*
  ManyToOne: User can have only one family but a Family can have many users
  FAMILY_ID is a foreigner key here in User and it points to the Family table
  It creates a join to lazily fetch the family of the user
  */
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="FAMILY_ID")
  //@ToString.Exclude
  private Family family;

  public Member() {
    this("", Role.CHILD);
  }

  public Member(String name, Role role) {
    log.error("Member:" + name + "," + role);
    this.id = UUID.randomUUID();
    this.name = name;
    this.role = role;
  }

}
