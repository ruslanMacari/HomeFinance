package ruslan.macari.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ruslan.macari.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    
    @Query(value ="select u from User u")
    List<User> listLimit(Pageable pageable);
}
