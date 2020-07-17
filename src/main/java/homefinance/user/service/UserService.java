package homefinance.user.service;

import homefinance.user.entity.User;
import homefinance.common.exception.DuplicateFieldsException;
import java.util.List;

public interface UserService {

  User add(User user) throws DuplicateFieldsException;

  void registerUser(String name, String password);

  void update(User user);

  List<User> list();

  List<User> listLimit(Integer limit);

  User getByName(String name);

  User getById(int id);

  void delete(Integer id);

  User getByNameAndPassword(String name, String password);

  List<User> getSimpleUsers();

  List<User> usersExceptRoot();
}
