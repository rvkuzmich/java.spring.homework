package kuzmich.hw3.controllers;

import kuzmich.hw3.model.Timesheet;
import kuzmich.hw3.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/timesheets")
public class TimesheetController {
    @Autowired
    private final TimesheetService service;
    @Autowired
    private final ProjectController projectController;

    public TimesheetController(TimesheetService timesheetService, ProjectController projectController) {
        this.service = timesheetService;
        this.projectController = projectController;
    }

    @GetMapping("/{timesheetId}")
    public ResponseEntity<Timesheet> get(@PathVariable("timesheetId") Long id) {
        Optional<Timesheet> ts = service.getById(id);
        if (ts.isPresent()) {
            return ResponseEntity.ok().body(ts.get());
            //return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet, @PathVariable("projectId") Long projectId) {
        if (projectController.getProjectById(projectId).getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            timesheet.setProjectId(projectId);
            timesheet = service.create(timesheet);
            return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{timesheetId}")
    public ResponseEntity<Void> delete(@PathVariable("timesheetId") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/timesheets?createdAtAfter={year}-{month}-{day}")
    public ResponseEntity<List<Timesheet>> filterCreatedAtAfter(@RequestParam("year") int year,
                                                                @RequestParam("month") int month,
                                                                @RequestParam("day") int day) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0,0);
        return ResponseEntity.status(HttpStatus.FOUND).body(service.filterByDateAfter(localDateTime));
    }

    @GetMapping("/timesheets?createdAtBefore={year}-{month}-{day}")
    public ResponseEntity<List<Timesheet>> filterCreatedAtBefore(@RequestParam("year") int year,
                                                                 @RequestParam("month") int month,
                                                                 @RequestParam("day")int day) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
        return ResponseEntity.status(HttpStatus.FOUND).body(service.filterByDateBefore(localDateTime));
    }
}
