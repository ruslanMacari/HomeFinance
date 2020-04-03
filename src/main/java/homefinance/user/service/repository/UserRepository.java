package homefinance.user.service.repository;

import homefinance.common.security.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByName(String name);

  User findByNameAndPassword(String name, String password);

  @Query("select u from User u")
  List<User> listLimit(Pageable pageable);

  @Query("select user \n"
      + "from User user \n"
      + "where user not in (select uRole.user from UserRole uRole where uRole.role = 'ADMIN')")
  List<User> getSimpleUsers();

  @Query("select u from User u where u.name <> :rootName")
  List<User> usersExceptRoot(@Param("rootName") String rootName);

  @Query("select u from User u where u.name = :name and u.id <> :id")
  User getByNameExceptID(@Param("name") String name, @Param("id") Integer id);

}
