package homefinance.user.service.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import homefinance.AbstractSpringIntegrationTest;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class UserServiceImplIntegrationTest extends AbstractSpringIntegrationTest {

  @Autowired
  private UserServiceImpl userService;
  private User user;

  @Value("${db.username}")
  private String rootname;

  public UserServiceImplIntegrationTest() {
    this.user = new User("test", "pass");
    this.user.setOneRole(Role.USER);
  }

  @Before
  public void before() {
    this.userService.list().forEach(u -> this.userService.delete(u.getId()));
  }

  @Test
  public void testAdd() {
    this.userService.add(this.user);
    List<User> list = this.userService.list();
    assertEquals(1, list.size());
    assertEquals(this.user, list.get(0));

  }

  @Test
  public void testUpdate() {
    this.userService.add(this.user);
    String newName = "user new";
    this.user.setName(newName);
    this.userService.update(this.user);
    String dbUserName = this.userService.getById(this.user.getId()).getName();
    assertEquals(dbUserName, newName);
  }

  @Test
  public void testList() {
    this.addThreeUsers();
    assertEquals(3, this.userService.list().size());
  }

  private void addThreeUsers() {
    List<User> list = new ArrayList<>(3);
    list.add(new User("test1", "pass"));
    list.add(new User("test2", "pass"));
    list.add(new User("test3", "pass"));
    list.forEach(user -> this.userService.add(user));
  }

  @Test
  public void testListLimit() {
    this.addThreeUsers();
    assertEquals(2, this.userService.listLimit(2).size());
  }

  @Test
  public void testGetById() {
    this.userService.add(this.user);
    User foundUser = this.userService.getById(this.user.getId());
    assertEquals(foundUser, this.user);
  }

  @Test
  public void testGetByName() {
    this.userService.add(this.user);
    User foundUser = this.userService.getByName(this.user.getName());
    assertEquals(foundUser, this.user);
  }

  @Test
  public void testDelete() {
    this.userService.add(this.user);
    this.userService.delete(this.user.getId());
    assertTrue(this.userService.list().isEmpty());
  }

  @Test
  public void testGetByNameAndPassword() {
    String name = "test GetByNameAndPassword";
    String password = "password";
    User userNamePass = new User(name, password);
    this.userService.add(userNamePass);
    User foundUser = this.userService.getByNameAndPassword(name, password);
    assertEquals(foundUser, userNamePass);
  }

  @Test
  public void testGetSimpleUsers() {
    User admin = new User("admin", "pass");
    admin.setOneRole(Role.ADMIN);
    this.userService.add(admin);
    this.userService.add(this.user);
    List<User> simpleUsers = this.userService.getSimpleUsers();
    assertEquals(1, simpleUsers.size());
    assertThat(simpleUsers.stream().findFirst().get(), is(this.user));
  }


  @Test
  public void testUsersExceptRoot() {
    User root = new User(this.rootname, "pass");
    this.userService.add(root);
    List<User> result = this.userService.usersExceptRoot();
    assertTrue(result.isEmpty());
  }

}
