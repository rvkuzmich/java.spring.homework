package kuzmich.hw5.controllers;

import kuzmich.hw5.model.Employee;
import kuzmich.hw5.model.Project;
import kuzmich.hw5.model.Timesheet;
import kuzmich.hw5.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("employeeId") Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{employeeId}/timesheets")
    public ResponseEntity<List<Timesheet>> getAllEmployeeTimesheets(@PathVariable("employeeId") Long id) {
        try {
            return ResponseEntity.ok(employeeService.getTimesheets(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{employeeId}/projects/{projectId}")
    public ResponseEntity<Void> setProject(@PathVariable("employeeId") Long id,
                                           @PathVariable("projectId") Long projectId) {
        employeeService.addProject(id, projectId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee (@PathVariable("employeeId") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
