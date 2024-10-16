pakcage kuzmich.hw7.repositories;


public interface UserRoleRepository implements JpaRepository<UserRole, Long> {

  List<UserRole> findByUserId(Long id);
}
