package kuzmich.hw9.client;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeResponse {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private Set<ProjectResponse> projects;

}
