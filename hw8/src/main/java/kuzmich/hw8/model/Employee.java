package kuzmich.hw8.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@Table(name = "employee")
@Schema(description = "Тип сотрудника")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    @Schema(description = "Идентификационный номер сотрудника")
    private Long employeeId;
    @Schema(description = "Имя сотрудника")
    private String firstName;
    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "project_id",
                    referencedColumnName = "projectId"))
    @Schema(description = "Проекты, в которых задействован сотрудник")
    private Set<Project> projects;
}
