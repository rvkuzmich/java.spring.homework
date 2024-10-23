package kuzmich.controllers;

import kuzmich.model.Employee;
import kuzmich.model.Project;
import kuzmich.model.Timesheet;
import kuzmich.repositories.EmployeeRepository;
import kuzmich.repositories.ProjectRepository;
import kuzmich.repositories.TimesheetRepository;
import kuzmich.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimesheetControllerTest {

    @Autowired
    TimesheetRepository timesheetRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        timesheetRepository.deleteAll();
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getByIdNotFound() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            ResponseEntity<Void> response = restClient.get()
                    .uri("/timesheets/-1")
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    @Test
    void getByIdAllOk() {
        Project testProject = new Project();
        testProject.setProjectName("NewProjectName");
        ResponseEntity<Project> projectResponse = restClient.post()
                .uri("/projects")
                .body(testProject)
                .retrieve()
                .toEntity(Project.class);

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("Roman");
        testEmployee.setLastName("Kuzmich");
        ResponseEntity<Employee> employeeResponse = restClient.post()
                .uri("/employees")
                .body(testEmployee)
                .retrieve()
                .toEntity(Employee.class);

        Timesheet timesheet = new Timesheet();
        timesheet.setCreatedAt(LocalDate.now());
        timesheet.setMinutes(300);
        timesheet.setProjectId(Objects.requireNonNull(projectResponse.getBody()).getProjectId());
        timesheet.setEmployeeId(Objects.requireNonNull(employeeResponse.getBody()).getEmployeeId());
        Timesheet timesheetExpected = timesheetRepository.save(timesheet);

        ResponseEntity<Timesheet> actual = restClient.get()
                .uri("/timesheets/" + timesheetExpected.getId())
                .retrieve().toEntity(Timesheet.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Timesheet responseBody = actual.getBody();

        assertNotNull(responseBody);
        assertEquals(timesheetExpected.getId(), responseBody.getId());
        assertEquals(timesheetExpected.getMinutes(), responseBody.getMinutes());
        assertEquals(timesheetExpected.getCreatedAt(), responseBody.getCreatedAt());
        assertEquals(timesheetExpected.getEmployeeId(), responseBody.getEmployeeId());
        assertEquals(timesheetExpected.getProjectId(), responseBody.getProjectId());
    }

    @Test
    void getAllTimesheets() {
        List<Timesheet> timesheetList = new ArrayList<>();
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setCreatedAt(LocalDate.now());
        timesheet1.setMinutes(300);
        timesheetRepository.save(timesheet1);
        Timesheet timesheet2 = new Timesheet();
        timesheet2.setCreatedAt(LocalDate.now());
        timesheet2.setMinutes(200);
        timesheetRepository.save(timesheet2);
        timesheetList.add(timesheet1);
        timesheetList.add(timesheet2);


        ResponseEntity<List<Timesheet>> actual = restClient.get()
                .uri("/timesheets")
                .retrieve().toEntity(new ParameterizedTypeReference<List<Timesheet>>() {
                });
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(timesheetList, actual.getBody());
    }

    @Test
    void testCreate() {
        Project testProject = new Project();
        testProject.setProjectName("NewProjectName");
        ResponseEntity<Project> projectResponse = restClient.post()
                .uri("/projects")
                .body(testProject)
                .retrieve()
                .toEntity(Project.class);

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("Roman");
        testEmployee.setLastName("Kuzmich");
        ResponseEntity<Employee> employeeResponse = restClient.post()
                .uri("/employees")
                .body(testEmployee)
                .retrieve()
                .toEntity(Employee.class);

        Timesheet timesheetToCreate = new Timesheet();
        timesheetToCreate.setCreatedAt(LocalDate.now());
        timesheetToCreate.setMinutes(300);
        timesheetToCreate.setProjectId(projectResponse.getBody().getProjectId());
        timesheetToCreate.setEmployeeId(employeeResponse.getBody().getEmployeeId());

        ResponseEntity<Timesheet> timesheetResponse = restClient.post()
                .uri("/timesheets")
                .body(timesheetToCreate)
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.CREATED, timesheetResponse.getStatusCode());

        Timesheet timesheetResponseBody = timesheetResponse.getBody();
        assertNotNull(timesheetResponseBody);
        assertNotNull(timesheetResponseBody.getId());
        assertEquals(timesheetToCreate.getId(), timesheetResponseBody.getId());
        assertEquals(timesheetToCreate.getCreatedAt(), timesheetResponseBody.getCreatedAt());
        assertEquals(timesheetToCreate.getMinutes(), timesheetResponseBody.getMinutes());
        assertEquals(timesheetToCreate.getEmployeeId(), timesheetResponseBody.getEmployeeId());
        assertEquals(timesheetToCreate.getProjectId(), timesheetResponseBody.getProjectId());

        assertTrue(timesheetRepository.existsById((timesheetResponseBody.getId())));
    }

    @Test
    void testDelete() {
        Timesheet toDelete = new Timesheet();
        toDelete.setMinutes(300);
        toDelete.setCreatedAt(LocalDate.now());
        toDelete = timesheetRepository.save(toDelete);

        ResponseEntity<Void> bodilessEntity = restClient.delete()
                .uri("/timesheets/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity();
        assertEquals(HttpStatus.NO_CONTENT, bodilessEntity.getStatusCode());

        assertFalse(timesheetRepository.existsById((toDelete.getId())));
    }
}