package kuzmich.hw4.services;

import kuzmich.hw4.model.Project;
import kuzmich.hw4.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.getById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.createProject(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteProject(id);
    }
}