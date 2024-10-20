package kuzmich.controllers;

import kuzmich.model.Timesheet;
import kuzmich.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timesheets")
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
        return  timesheetService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
