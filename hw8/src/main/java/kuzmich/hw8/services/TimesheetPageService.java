package kuzmich.hw8.services;

import kuzmich.hw8.controllers.TimesheetPageDto;
import kuzmich.hw8.model.Employee;
import kuzmich.hw8.model.Project;
import kuzmich.hw8.model.Timesheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public Optional<TimesheetPageDto> findById(Long id) {
        return timesheetService.getById(id)
                .map(this::convert);
    }

    private TimesheetPageDto convert(Timesheet timesheet) {
        Project project = projectService.getProjectById(timesheet.getProjectId())
                .orElseThrow();
        Employee employee = employeeService.findById(timesheet.getEmployeeId())
                .orElseThrow();

        TimesheetPageDto timesheetPageDto = new TimesheetPageDto();

        timesheetPageDto.setProjectId(String.valueOf(project.getProjectId()));
        timesheetPageDto.setProjectName(project.getProjectName());
        timesheetPageDto.setId(String.valueOf(timesheet.getId()));
        timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDto.setCreatedAt(String.valueOf(timesheet.getCreatedAt()));
        timesheetPageDto.setEmployeeId(String.valueOf(timesheet.getEmployeeId()));
        timesheetPageDto.setEmployeeFirstName(employee.getFirstName());
        timesheetPageDto.setEmployeeLastName(employee.getLastName());

        return timesheetPageDto;
    }

    public List<TimesheetPageDto> findAll() {
        return timesheetService.getAllTimesheets()
                .stream()
                .map(this::convert).toList();
    }
}
