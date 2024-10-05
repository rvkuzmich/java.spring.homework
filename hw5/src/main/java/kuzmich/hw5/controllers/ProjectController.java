package kuzmich.hw5.controllers;

import kuzmich.hw5.model.Project;
import kuzmich.hw5.model.Timesheet;
import kuzmich.hw5.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable("projectId") Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping("/{projectId}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheets(@PathVariable("projectId") Long id) {
        try {
            return ResponseEntity.ok(projectService.getTimesheets(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        project = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
