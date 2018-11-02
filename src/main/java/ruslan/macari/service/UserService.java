package ruslan.macari.service;

import java.util.List;
import ruslan.macari.domain.User;

public interface UserService {
    
    User add(User user);

    void update(User user);

    List<User> list();

    List<User> listLimit(int limit);

    User getById(int id);

    User getByName(String name);

    void delete(int id);
    
    User getAdmin();
    
    User getByNameAndPassword(String name, String password);
    
    List<User> getSimpleUsers();

    User getByNameExceptID(String name, int id);
}
