package hu.bp.netcafe.backend.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Family {
  @Id
  @Type(type = "pg-uuid")
  private UUID id;
  private String name;

  /*
  The 'mappedBy = "family"' attribute specifies that
  the 'private  Family family;' field in Member owns the
  relationship (i.e. contains the foreign key for the query to
  find all members of the family.)
  */
  /*
  @OneToMany(mappedBy = "family")
  private List<Member> members;
  */

  public Family() {
    this("");
  }

  public Family(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
  }
}
