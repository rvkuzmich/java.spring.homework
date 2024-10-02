package kuzmich.hw4.services;

import kuzmich.hw4.model.Timesheet;
import kuzmich.hw4.repositories.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class TimesheetService {
    @Autowired
    private final TimesheetRepository timesheetRepository;

    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Timesheet> getById(Long id) {
        return timesheetRepository.getById(id);
    }

    public List<Timesheet> getAllTimesheets() {
        return getAllTimesheets(null, null);
    }

    public List<Timesheet> getAllTimesheets(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        return timesheetRepository.getAllTimesheets(createdAtBefore, createdAtAfter);
    }

    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProjectId())) {
            throw new IllegalArgumentException("Project ID must be not null");
        }
        if (timesheetRepository.getById(timesheet.getProjectId()).isEmpty()) {
            throw new NoSuchElementException("Project with ID " + timesheet.getProjectId() + " doesn't exists");
        }
        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.create(timesheet);
    }

    public void delete(Long id) {
        timesheetRepository.delete(id);
    }

}