package kuzmich.controllers;

import kuzmich.model.Project;
import kuzmich.repositories.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
class ProjectControllerTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WebTestClient webTestClient;

    @LocalServerPort //- для веб-клиента не нужен порт
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void getProjectByIdNotFound() {
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            ResponseEntity<Void> response = restClient.get()
                    .uri("/projects/-2")
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    @Test
    void getProjectByIdAllOk() {
        //save(project)
        //GET /projects/1L => 200 OK

        //given
        Project project = new Project();
        project.setProjectName("projectName");
        Project expected = projectRepository.save(project);

        ResponseEntity<Project> actual = restClient.get()
                .uri("/projects/" + expected.getProjectId())
                .retrieve().toEntity(Project.class);

//        ResponseEntity<Project> entity = WebClient.create()
//                .get()
//                .uri()
//                .retrieve()
//                .toEntity(Project.class)
//                .block();

//        webTestClient.get()
//                .uri("/projects/" + expected.getProjectId())
//                .exchange() //retrieve
//                .expectStatus().isOk() // Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
//                .expectBody(Project.class)
//                        .value(actual -> {
//                            assertEquals(expected.getProjectId(), actual.getProjectId());
//                            assertEquals(expected.getProjectName(), actual.getProjectName());
//                        });

        // assert 200 OK
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Project responseBody = actual.getBody();

        assertNotNull(responseBody);
        assertEquals(expected.getProjectId(), responseBody.getProjectId());
        assertEquals(expected.getProjectName(), responseBody.getProjectName());
    }

    @Test
    void testCreate() {
        Project projectToCreate = new Project();
        projectToCreate.setProjectName("NewProjectName");

        ResponseEntity<Project> response = restClient.post()
                .uri("/projects")
                .body(projectToCreate)
                .retrieve()
                .toEntity(Project.class);
        //Проверяем http-ручку сервера
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Project responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getProjectId());
        assertEquals(projectToCreate.getProjectName(), responseBody.getProjectName());
        //Проверяем, что запись в БД есть
        assertTrue(projectRepository.existsById((responseBody.getProjectId())));
    }

    @Test
    void testDeleteById() {
        Project toDelete = new Project();
        toDelete.setProjectName("projectName");
        toDelete = projectRepository.save(toDelete);

        ResponseEntity<Void> bodilessEntity = restClient.delete()
                .uri("/projects/" + toDelete.getProjectId())
                .retrieve()
                .toBodilessEntity();
        assertEquals(HttpStatus.NO_CONTENT, bodilessEntity.getStatusCode());

        assertFalse(projectRepository.existsById((toDelete.getProjectId())));
    }

}