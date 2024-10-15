package kuzmich.hw7.controllers;

import lombok.Data;

@Data
public class TimesheetPageDto {

    private String projectId;
    private String projectName;
    private String id;
    private String minutes;
    private String createdAt;
    private String employeeId;
    private String employeeFirstName;
    private String employeeLastName;

}
