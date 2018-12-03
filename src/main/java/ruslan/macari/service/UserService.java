package ruslan.macari.service;

import java.util.List;
import ruslan.macari.security.User;

public interface UserService {
    
    User add(User user);

    void update(User user);

    List<User> list();

    List<User> listLimit(int limit);

    User getByName(String name);

    void delete(String id);
    
    User getRoot();
    
    User getByNameAndPassword(String name, String password);
    
    List<User> getSimpleUsers();

    User getByNameExceptID(String name, int id);
    
    List<User> usersExceptRoot();
}
