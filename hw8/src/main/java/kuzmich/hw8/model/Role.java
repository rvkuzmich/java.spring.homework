package kuzmich.hw8.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@Schema(description = "Тип роли")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private String roleId;

  @Column(name = "name")
  private String name;

  public Role(String name) {
    this.name = name;
  }
}
