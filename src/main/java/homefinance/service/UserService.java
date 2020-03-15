package homefinance.service;

import homefinance.security.User;
import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.List;

public interface UserService {

  User add(User user) throws DuplicateFieldsException;

  void registerUser(String name, String password);

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
