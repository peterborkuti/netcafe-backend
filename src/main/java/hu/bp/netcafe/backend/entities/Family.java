package hu.bp.netcafe.backend.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@Entity
public class Family {
  @Id
  @Type(type = "pg-uuid")
  private UUID id;
  private String name;

  public Family(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
  }
}