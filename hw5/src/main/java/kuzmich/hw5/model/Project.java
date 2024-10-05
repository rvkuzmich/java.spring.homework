package kuzmich.hw5.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long projectId;
    private String projectName;

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees;
}
