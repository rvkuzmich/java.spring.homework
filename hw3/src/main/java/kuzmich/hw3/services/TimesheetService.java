package kuzmich.hw3.services;

import kuzmich.hw3.model.Timesheet;
import kuzmich.hw3.repositories.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimesheetService {
    @Autowired
    private final TimesheetRepository repository;

    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.repository = timesheetRepository;
    }

    public Optional<Timesheet> getById(Long id) {
        return repository.getById(id);
    }

    public List<Timesheet> getAll() {
        return repository.getAll();
    }

    public Timesheet create(Timesheet timesheet) {
        timesheet.setCreatedAt(LocalDate.now());
        return repository.create(timesheet);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Timesheet> filterByDate(LocalDate createdAtBefore, LocalDate createdAfter) {
        return repository.filterByDate(createdAtBefore, createdAfter);
    }

}
