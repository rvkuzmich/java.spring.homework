package kuzmich.hw8.services;

import kuzmich.hw8.aspect.Timer;
import kuzmich.hw8.model.Timesheet;
import kuzmich.hw8.repositories.TimesheetRepository;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Timer() //(level = Level.DEBUG)
@Service
public class TimesheetService {
    @Autowired
    private final TimesheetRepository timesheetRepository;

    public TimesheetService(TimesheetService timesheetService) {
        this(timesheetService.timesheetRepository);
    }
    
    @Autowired
    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

//    @Timer
    public Optional<Timesheet> getById(Long id) {
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> getAllTimesheets() {
        return getAllTimesheets(null, null);
    }

    public List<Timesheet> getAllTimesheets(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        return timesheetRepository.findAll(createdAtBefore, createdAtAfter);
    }

    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProjectId())) {
            throw new IllegalArgumentException("Project ID must be not null");
        }
        if (timesheetRepository.findById(timesheet.getProjectId()).isEmpty()) {
            throw new NoSuchElementException("Project with ID " + timesheet.getProjectId() + " doesn't exists");
        }
        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.save(timesheet);
    }

//    @Timer(enabled = false)
    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }

}
