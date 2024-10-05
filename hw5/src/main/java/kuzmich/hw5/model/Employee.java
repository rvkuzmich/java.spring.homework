package kuzmich.hw5.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long employeeId;
    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "employee_id",
                    referencedColumnName = "projectId"))
    private List<Project> projects;
}
