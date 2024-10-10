package kuzmich.hw6.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kuzmich.hw6.model.Employee;
import kuzmich.hw6.model.Project;
import kuzmich.hw6.model.Timesheet;
import kuzmich.hw6.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Сотрудники", description = "API для работы с сотрудниками")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Получить сотрудников",
            description = "Получить всех сотрудников",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Employee.class))),
            }
    )
    @GetMapping
    public ResponseEntity<List<Employee>> findAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Operation(
            summary = "Получить сотрудника",
            description = "Получить сотрудника по идентификационному номеру",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(description = "Сотрудник не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("employeeId")
                                                         @Parameter(description = "Идентификатор сотрудника") Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить все листы учета рабочего времени, относящиеся к конкретному сотруднику",
            description = "Получить все листы учета рабочего времени, относящиеся к сотруднику," +
                    "определенному идентификационным номером",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(description = "Сотрудник не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{employeeId}/timesheets")
    public ResponseEntity<List<Timesheet>> getAllEmployeeTimesheets(@PathVariable("employeeId")
                                                                        @Parameter(description = "Идентификатор сотрудника") Long id) {
        try {
            return ResponseEntity.ok(employeeService.getTimesheets(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Назначить сотрудника на проект",
            description = "Назначить сотрудника, определенного идентификационным номером на проект",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Employee.class))),
            }
    )
    @PutMapping("/{employeeId}/projects")
    public ResponseEntity<Void> setProject(@RequestBody
                                               @Parameter(description = "Проект, на который назначается сотрудник") Project project,
                                           @PathVariable("employeeId")
                                           @Parameter(description = "Идентификатор сотрудника") Long id) {
        employeeService.addProject(id, project);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Создать сотрудника",
            description = "Создать нового сотрудника",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Employee.class))),
            }
    )
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody
                                                       @Parameter(description = "Сотрудник") Employee employee) {
        employee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @Operation(
            summary = "Удалить сотрудника",
            description = "Удалить сотрудника по идентификационному номеру",
            responses = {
                    @ApiResponse(description = "Сотрудник удален", responseCode = "204",
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee (@PathVariable("employeeId") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
