package homefinance.user.service.impl;

import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.util.ConstraintPersist;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import homefinance.user.service.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private String rootName;
  private String rootPassword;
  private PasswordEncoder encoder;

  @Value("${db.password}")
  public void setRootPassword(String rootPassword) {
    this.rootPassword = rootPassword;
  }

  @Value("${db.username}")
  public void setRootName(String rootName) {
    this.rootName = rootName;
  }

  @Autowired
  public void setEncoder(PasswordEncoder encoder) {
    this.encoder = encoder;
  }

  @PostConstruct
  public void init() {
    this.createRootUserIfNotExist();
  }

  private void createRootUserIfNotExist() {
    if (this.getByName(this.rootName) != null) {
      return;
    }
    User root = new User();
    root.setName(this.rootName);
    root.setPassword(this.encoder.encode(this.rootPassword));
    root.setOneRole(Role.ADMIN);
    this.add(root);
  }

  private ConstraintPersist constraintPersist;

  @Autowired
  public void setConstraintPersist(ConstraintPersist constraintPersist) {
    this.constraintPersist = constraintPersist;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User add(User user) throws DuplicateFieldsException {
    return (User) this.constraintPersist
        .add(() -> this.userRepository.saveAndFlush(user), user.getConstraintsMap());
  }

  @Override
  public void registerUser(String name, String password) {
    User user = new User(name);
    user.setPassword(this.encoder.encode(password));
    user.setEnabled(true);
    user.setOneRole(Role.USER);
    this.add(user);
  }

  @Override
  public void update(User user) {
    this.constraintPersist.update(() -> this.userRepository.saveAndFlush(user), user.getConstraintsMap());
  }

  @Override
  public List<User> list() {
    return this.userRepository.findAll();
  }

  @Override
  public List<User> listLimit(Integer limit) {
    return this.userRepository.listLimit(new PageRequest(0, limit));
  }

  @Override
  public User getByName(String name) {
    return this.userRepository.findByName(name);
  }

  @Override
  public void delete(Integer id) {
    this.userRepository.deleteById(id);
  }

  @Override
  public User getByNameAndPassword(String name, String password) {
    return this.userRepository.findByNameAndPassword(name, password);
  }

  @Override
  public List<User> getSimpleUsers() {
    // TODO: 24.05.2020 RMACARI: change to return only ordered user names
    return this.userRepository.getSimpleUsers();
  }

  @Override
  public List<User> usersExceptRoot() {
    List<User> users = this.userRepository.usersExceptRoot(this.rootName);
    return Objects.nonNull(users) ? users : new ArrayList<>();
  }

  @Override
  public User getById(Integer id) {
    return this.userRepository.findById(id).orElse(null);
  }

}
