package homefinance.user.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import homefinance.AbstractSpringIntegrationTest;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
// TODO: 021, 21-Nov-22 ruslan.macari: add test containers
public class UserServiceImplIntegrationTest extends AbstractSpringIntegrationTest {

  @Autowired
  private UserServiceImpl userService;
  private User user;

  public UserServiceImplIntegrationTest() {
    user = new User("test", "pass");
    user.setOneRole(Role.USER);
  }

  @BeforeEach
  public void before() {
    userService.list().forEach(u -> userService.delete(u.getId()));
  }

  @Test
  public void testAdd() {
    userService.add(user);
    List<User> list = userService.list();
    assertEquals(1, list.size());
    assertEquals(user, list.get(0));

  }

  @Test
  public void testUpdate() {
    userService.add(user);
    String newName = "user new";
    user.setName(newName);
    userService.update(user);
    String dbUserName = userService.getById(user.getId()).getName();
    assertEquals(dbUserName, newName);
  }

  @Test
  public void testList() {
    addThreeUsers();
    assertEquals(3, userService.list().size());
  }

  private void addThreeUsers() {
    List<User> list = new ArrayList<>(3);
    list.add(new User("test1", "pass"));
    list.add(new User("test2", "pass"));
    list.add(new User("test3", "pass"));
    list.forEach(user -> userService.add(user));
  }

  @Test
  public void testListLimit() {
    addThreeUsers();
    assertEquals(2, userService.listLimit(2).size());
  }

  @Test
  public void testGetById() {
    userService.add(user);
    User foundUser = userService.getById(user.getId());
    assertEquals(foundUser, user);
  }

  @Test
  public void testGetByName() {
    userService.add(user);
    User foundUser = userService.getByName(user.getName());
    assertEquals(foundUser, user);
  }

  @Test
  public void testDelete() {
    userService.add(user);
    userService.delete(user.getId());
    assertTrue(userService.list().isEmpty());
  }

  @Test
  public void testGetByNameAndPassword() {
    String name = "test GetByNameAndPassword";
    String password = "password";
    User userNamePass = new User(name, password);
    userService.add(userNamePass);
    User foundUser = userService.getByNameAndPassword(name, password);
    assertEquals(foundUser, userNamePass);
  }

  @Test
  public void testGetSimpleUsers() {
    User admin = new User("admin", "pass");
    admin.setOneRole(Role.ADMIN);
    userService.add(admin);
    userService.add(user);
    List<User> simpleUsers = userService.getSimpleUsers();
    assertEquals(1, simpleUsers.size());
    assertThat(simpleUsers.stream().findFirst().get(), is(user));
  }


  @Test
  public void testUsersExceptRoot() {
    User root = new User("root", "pass");
    userService.add(root);
    List<User> result = userService.usersExceptRoot();
    assertTrue(result.isEmpty());
  }

}
