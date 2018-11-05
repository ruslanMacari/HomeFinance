package ruslan.macari.service.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ruslan.macari.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    
    User findByNameAndPassword(String name, String password);
    
    @Query(value ="select u from User u")
    List<User> listLimit(Pageable pageable);
    
    @Query(value ="select u from User u where u.admin = true")
    List<User> findAdmins();
    
    @Query(value ="select u from User u where u.admin = false")
    List<User> getSimpleUsers();
    
    @Query(value ="select u from User u where u.name = :name and u.id <> :id")
    User getByNameExceptID(@Param("name")String name, @Param("id")int id);
}
