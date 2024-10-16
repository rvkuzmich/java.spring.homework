pakcage kuzmich.hw7.repositories;


public interface UserRoleRepository implements JpaRepository<UserRole, Long> {

  List<String> findRoleNameByUserId(Long id);
}
