package ruslan.macari.service;

import java.util.List;
import ruslan.macari.domain.User;

public interface UserService {
    
    User addUser(User user);

    void updateUser(User user);

    List<User> listUsers();

    List<User> listUsersLimit(int limit);

    User getUserById(int id);

    User getUserByName(String name);

    void removeUser(int id);

}
