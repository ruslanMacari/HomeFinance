package homefinance.user.service.impl;

import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.util.ConstraintPersist;
import homefinance.user.UserFields;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import homefinance.user.service.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ConstraintPersist constraintPersist;
  private final PasswordEncoder encoder;
  private String rootName;
  private String rootPassword;

  public UserServiceImpl(UserRepository userRepository, ConstraintPersist constraintPersist,
      PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.constraintPersist = constraintPersist;
    this.encoder = encoder;
  }

  @Value("${db.password}")
  public void setRootPassword(String rootPassword) {
    this.rootPassword = rootPassword;
  }

  @Value("${db.username}")
  public void setRootName(String rootName) {
    this.rootName = rootName;
  }

  @PostConstruct
  public void init() {
    createRootUserIfNotExist();
  }

  private void createRootUserIfNotExist() {
    if (getByName(rootName) != null) {
      return;
    }
    User root = new User();
    root.setName(rootName);
    root.setPassword(encoder.encode(rootPassword));
    root.setOneRole(Role.ADMIN);
    add(root);
  }

  @Override
  public User getByName(String name) {
    return userRepository.findByName(name);
  }

  @Override
  public User add(User user) throws DuplicateFieldsException {
    return (User) constraintPersist.add(() -> userRepository.saveAndFlush(user), user.getConstraintsMap());
  }

  @Override
  public void registerUser(String name, String password) {
    User user = new User(name);
    user.setPassword(encoder.encode(password));
    user.setEnabled(true);
    user.setOneRole(Role.USER);
    add(user);
  }

  @Override
  public void update(User user) {
    constraintPersist.update(() -> userRepository.saveAndFlush(user), user.getConstraintsMap());
  }

  @Override
  public void update(UserFields userFields) {
    User user = userRepository.getOne(userFields.getId());
    user.setName(userFields.getName());
    user.setPassword(encoder.encode(userFields.getPassword()));
    user.addRole(userFields.isAdmin() ? Role.ADMIN : Role.USER);
    constraintPersist.update(() -> userRepository.saveAndFlush(user), user.getConstraintsMap());
  }

  @Override
  public void updateWithoutPassword(UserFields userFields) {
    User user = userRepository.getOne(userFields.getId());
    user.setName(userFields.getName());
    user.addRole(userFields.isAdmin() ? Role.ADMIN : Role.USER);
    constraintPersist.update(() -> userRepository.saveAndFlush(user), user.getConstraintsMap());
  }

  @Override
  public List<User> list() {
    return userRepository.findAll();
  }

  @Override
  public List<User> listLimit(Integer limit) {
    return userRepository.listLimit(new PageRequest(0, limit));
  }

  @Override
  public void delete(Integer id) {
    userRepository.deleteById(id);
  }

  @Override
  public User getByNameAndPassword(String name, String password) {
    return userRepository.findByNameAndPassword(name, password);
  }

  @Override
  public List<User> getSimpleUsers() {
    // TODO: 24.05.2020 RMACARI: change to return only ordered user names
    return userRepository.getSimpleUsers();
  }

  @Override
  public List<User> usersExceptRoot() {
    List<User> users = userRepository.usersExceptRoot(rootName);
    return Objects.nonNull(users) ? users : new ArrayList<>();
  }

  @Override
  public User getById(int id) {
    return userRepository.findById(id).orElse(null);
  }

}
