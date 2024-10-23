package kuzmich.services;

import kuzmich.model.Project;
import kuzmich.repositories.ProjectRepository;
import kuzmich.repositories.TimesheetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest//(classes = {ProjectService.class, ProjectRepository.class, TimesheetRepository.class})
class ProjectServiceTest {

    @Autowired//@MockBean - когда указываются конкретные бины для поднятия в тесте
    ProjectRepository projectRepository;
    @MockBean
    TimesheetRepository timesheetRepository;

    @Autowired
    ProjectService projectService;

    @Test
    void getProjectByIdEmpty() {
        //given
        assertFalse(projectRepository.existsById(2L));

        assertTrue(projectService.getProjectById(2L).isEmpty());
    }

    @Test
    void getProjectByIdPresent() {
        //given
        Project project = new Project();
        project.setProjectName("projectName");
        projectRepository.save(project);

        //when
        Optional<Project> actual = projectService.getProjectById(project.getProjectId());

        //then
        assertTrue(actual.isPresent());
        assertEquals(actual.get().getProjectId(), project.getProjectId());
        assertEquals(actual.get().getProjectName(), project.getProjectName());
    }
}