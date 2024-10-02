package kuzmich.hw4.controllers;

import kuzmich.hw4.model.Timesheet;
import kuzmich.hw4.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/timesheets")
public class TimesheetController {
    @Autowired
    private final TimesheetService timesheetService;
    @Autowired
    private final ProjectController projectController;

    public TimesheetController(TimesheetService timesheetService, ProjectController projectController) {
        this.timesheetService = timesheetService;
        this.projectController = projectController;
    }

    @GetMapping("/{timesheetId}")
    public ResponseEntity<Timesheet> get(@PathVariable("timesheetId") Long id) {
        Optional<Timesheet> ts = timesheetService.getById(id);
        if (ts.isPresent()) {
            return ResponseEntity.ok().body(ts.get());
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<Timesheet>> getAllTimesheets(@RequestParam(required = false) LocalDate createdAtBefore,
                                                            @RequestParam(required = false) LocalDate createdAtAfter) {
        return ResponseEntity.ok(timesheetService.getAllTimesheets(createdAtBefore, createdAtAfter));
    }

    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet, @PathVariable("projectId") Long projectId) {
        if (projectController.getProjectById(projectId).getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            timesheet.setProjectId(projectId);
            timesheet = timesheetService.create(timesheet);
            return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{timesheetId}")
    public ResponseEntity<Void> delete(@PathVariable("timesheetId") Long id) {
        timesheetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}