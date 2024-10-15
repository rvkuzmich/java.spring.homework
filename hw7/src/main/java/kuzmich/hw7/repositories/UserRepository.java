package kuzmich.hw7.repositories;

import kuzmich.hw7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
