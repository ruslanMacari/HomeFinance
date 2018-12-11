package ruslan.macari.service;

import java.util.List;
import ruslan.macari.security.User;

public interface UserService {
    
    User add(User user);

    void update(User user);

    List<User> list();

    List<User> listLimit(Integer limit);

    User getByName(String name);
    
    User getById(Integer id);

    void delete(Integer id);
    
    User getByNameAndPassword(String name, String password);
    
    List<User> getSimpleUsers();

    List<User> usersExceptRoot();
}
