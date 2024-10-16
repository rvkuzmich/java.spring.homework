package kuzmich.hw7.model;

//import jakarta.persistance.*;

//@Entity
//@Table(name = "roles")
public enum Role {

  ADMIN("admin"), USER("user"), REST("rest");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
//  @Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE)
//  private Long id;

//  @Column(name = "name")
//  private String name;
  
}
