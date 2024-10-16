package kuzmich.hw7.model;

import jakarta.persistance.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users_roles")
public class UserRole {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "role_name")
  private String roleName;
}
