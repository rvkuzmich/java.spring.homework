package kuzmich.hw3.repositories;

import kuzmich.hw3.model.Timesheet;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TimesheetRepository {
    private static Long sequence = 1L;
    private final List<Timesheet> timesheets = new ArrayList<>();

    public Optional<Timesheet> getById(Long id) {
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();
    }

    public List<Timesheet> getAll() {
        return List.copyOf(timesheets);
    }

    public Timesheet create(Timesheet timesheet) {
        timesheet.setId(sequence++);
        timesheets.add(timesheet);
        return timesheet;
    }

    public void delete(Long id) {
        timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .ifPresent(timesheets::remove);
    }

    public List<Timesheet> filterByDateAfter(LocalDateTime createdAtAfter) {
        List<Timesheet> res = timesheets.stream()
                .filter(t -> t.getCreatedAt().isAfter(ChronoLocalDate.from(createdAtAfter)))
                .toList();
        return res;
    }

    public List<Timesheet> filterByDateBefore(LocalDateTime createdAtBefore) {
        List<Timesheet> res = timesheets.stream()
                .filter(t -> t.getCreatedAt().isAfter(ChronoLocalDate.from(createdAtBefore)))
                .toList();
        return res;
    }
}
