package kuzmich.hw7.repositories;

import kuzmich.hw7.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    default List<Timesheet> findAll(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        if(createdAtBefore == null && createdAtAfter == null) {
            return findAll();
        } else if (createdAtBefore == null) {
            return findByCreatedAtGreaterThan(createdAtAfter);
        } else if (createdAtAfter == null) {
            return findByCreatedAtLessThan(createdAtBefore);
        } else {
            return findByCreatedAtBetween(createdAtAfter, createdAtBefore);
        }
    }

    @Query("select t from Timesheet t where t.projectId = :projectId order by t.createdAt desc")
    List<Timesheet> findByProjectId(Long projectId);

    @Query("select t from Timesheet t where t.employeeId = :employeeId order by t.createdAt desc")
    List<Timesheet> findByEmployeeId(Long employeeId);

    List<Timesheet> findByCreatedAtGreaterThan(LocalDate createdAtAfter);
    List<Timesheet> findByCreatedAtLessThan(LocalDate createdAtBefore);
    List<Timesheet> findByCreatedAtBetween(LocalDate min, LocalDate max);

}
