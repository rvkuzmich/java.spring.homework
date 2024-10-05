package kuzmich.hw5.repositories;

import kuzmich.hw5.model.Employee;
import kuzmich.hw5.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
