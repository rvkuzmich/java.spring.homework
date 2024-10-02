package kuzmich.hw4.services;

import kuzmich.hw4.controllers.TimesheetPageDto;
import kuzmich.hw4.model.Project;
import kuzmich.hw4.model.Timesheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public Optional<TimesheetPageDto> findById(Long id) {
        return timesheetService.getById(id)
                .map(this::convert);
    }

    private TimesheetPageDto convert(Timesheet timesheet) {
        Project project = projectService.getProjectById(timesheet.getProjectId())
                .orElseThrow();

        TimesheetPageDto timesheetPageDto = new TimesheetPageDto();

        timesheetPageDto.setProjectId(String.valueOf(project.getId()));
        timesheetPageDto.setProjectName(project.getProjectName());
        timesheetPageDto.setId(String.valueOf(timesheet.getId()));
        timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDto.setCreatedAt(String.valueOf(timesheet.getCreatedAt()));

        return timesheetPageDto;
    }

    public List<TimesheetPageDto> findAll() {
        return timesheetService.getAllTimesheets()
                .stream()
                .map(this::convert).toList();
    }
}
