package homefinance.user.service;

import homefinance.common.exception.DuplicateFieldsException;
import homefinance.user.UserFields;
import homefinance.user.entity.User;
import java.util.List;

public interface UserService {

  User add(User user) throws DuplicateFieldsException;

  void registerUser(String name, String password);

  void update(User user);

  void update(UserFields userFields);

  void updateWithoutPassword(UserFields userFields);

  List<User> list();

  List<User> listLimit(Integer limit);

  User getByName(String name);

  User getById(Long id);

  void delete(Long id);

  User getByNameAndPassword(String name, String password);

  List<User> getSimpleUsers();

  List<User> usersExceptRoot();
}
