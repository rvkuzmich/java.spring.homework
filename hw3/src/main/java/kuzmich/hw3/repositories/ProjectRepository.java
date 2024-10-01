package kuzmich.hw3.repositories;

import kuzmich.hw3.model.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class ProjectRepository {

    private List<Project> projects = new ArrayList<>();
    private Long sequence = 1L;

    public List<Project> getAllProjects() {
        return List.copyOf(projects);
    }

    public Optional<Project> getById(Long id) {
        return projects.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst();
    }

    public Project createProject(Project project) {
        project.setId(sequence++);
        projects.add(project);
        return project;
    }

    public void deleteProject(Long id) {
        projects.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .ifPresent(projects::remove);
    }
}
