package kuzmich.hw6.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kuzmich.hw6.model.Project;
import kuzmich.hw6.model.Timesheet;
import kuzmich.hw6.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/projects")
@Tag(name = "Проекты", description = "API для работы с проектами")
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Получить проекты",
            description = "Получить все проекты",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Project.class))),
            }
    )
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @Operation(
            summary = "Получить проект",
            description = "Получить проект по идентификационному номеру",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(description = "Проект не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable("projectId")
                                                      @Parameter(description = "Идентификатор проекта") Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить все листы учета рабочего времени, относящиеся к конкретному проекту",
            description = "Получить все листы учета рабочего времени, относящиеся к проекту," +
                    "определенному идентификационным номером",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(description = "Проект не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @RequestMapping("/{projectId}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheets(@PathVariable("projectId")
                                                             @Parameter(description = "Идентификатор проекта") Long id) {
        try {
            return ResponseEntity.ok(projectService.getTimesheets(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Создать проект",
            description = "Создать новый проект",
            responses = {
                    @ApiResponse(description = "Ответ об успешной операции", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Project.class))),
            }
    )
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody @Parameter(description = "Проект для создания") Project project) {
        project = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(
            summary = "Удалить проект",
            description = "Удалить проект по идентификационному номеру",
            responses = {
                    @ApiResponse(description = "Проект удален", responseCode = "204",
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") @Parameter(description = "Идентификатор проекта") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
