package kuzmich.hw7.model;

import jakarta.persistance.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@Schema(description = "Тип роли")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private String roleId

  @Column(name = "name")
  private String name;
}
