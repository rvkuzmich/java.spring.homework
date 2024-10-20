package kuzmich.services;

import kuzmich.aspect.Recover;
import kuzmich.model.Project;
import kuzmich.model.Timesheet;
import kuzmich.repositories.ProjectRepository;
import kuzmich.repositories.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Recover
    public Project createProject(Project project) throws Exception {
        throw new Exception("smthg go wrong");
//        throw new NoSuchFieldException("test");
//        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Timesheet> getTimesheets(Long id) {
        if (projectRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Project id " + id + " doesn't exist");
        }
        return timesheetRepository.findByProjectId(id);
    }
}
