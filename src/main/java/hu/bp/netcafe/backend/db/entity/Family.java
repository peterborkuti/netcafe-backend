package hu.bp.netcafe.backend.db.entity;

import lombok.Data;


import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Family {
  @Id
  @Column( columnDefinition = "uuid", updatable = false )
  private UUID id;

  private String name;

  /*
  The 'mappedBy = "family"' attribute specifies that
  the 'private  Family family;' field in Member owns the
  relationship (i.e. contains the foreign key for the query to
  find all members of the family.)
  */
  @ToString.Exclude
  @OneToMany(mappedBy = "family", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
  private List<Member> members;

  public Family() {
    this("");
  }

  public Family(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.members = new ArrayList<>();
  }

  public Family(String name, UUID id) {
    this.id = id;
    this.name = name;
    this.members = new ArrayList<>();
  }

  public Family(String name, UUID id, List<Member> members) {
    this.id = id;
    this.name = name;
    this.members = members;
  }

  public void addMember(Member member) {
    member.setFamily(this);
    members.add(member);
  }

  public void removeMember(Member member) {
    members.remove(member);
    member.setFamily(null);
  }

}
