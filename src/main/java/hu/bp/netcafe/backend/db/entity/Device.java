package hu.bp.netcafe.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Slf4j
public class Device {
  @Id
  @Column( columnDefinition = "uuid", updatable = false )
  private UUID id;

  private String name;

  private String macAddress;

  private int remainingTime;

  private int allocatedTime;

  private boolean onNet;

  /*
  ManyToOne: User can have only one family but a Family can have many users
  FAMILY_ID is a foreigner key here in User and it points to the Family table
  It creates a join to lazily fetch the family of the user
  */
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="MEMBER_ID")
  @JsonIgnore
  private Member owner;

  public Device() {
    this("", "");
  }

  public Device(String name, String macAddress) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.macAddress = macAddress;
    this.remainingTime = 0;
    this.allocatedTime = 0;
    this.onNet = false;
  }
}
