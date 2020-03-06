package homefinance.web;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.security.User;
import homefinance.service.UserService;
import homefinance.util.PathSelector;
import homefinance.util.impl.PathSelectorTest;
import homefinance.web.exceptions.PageNotFoundException;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

public class UsersControllerTest {

  private UsersController usersController;
  private Model model;
  private UserService userService;
  private PasswordEncoder encoder;
  private String rootname;
  private BindingResult result;
  private User user;
  private Validator validator;
  private PathSelector pathSelector;

  public UsersControllerTest() {
    userService = mock(UserService.class);
    encoder = mock(PasswordEncoder.class);
    rootname = "root";
    validator = mock(Validator.class);
    usersController = new UsersController();
    usersController.setUserService(userService);
    usersController.setEncoder(encoder);
    usersController.setRootname(rootname);
    model = mock(Model.class);
    result = mock(BindingResult.class);
    user = mock(User.class);
    pathSelector = new PathSelectorTest();
    usersController.setPathSelector(pathSelector);
  }

  @Test
  public void testList() {
    System.out.println("list");
    assertTrue(usersController.list(model).equals(UsersController.LIST_PATH));
  }

  @Test
  public void testView() {
    System.out.println("view");
    Integer id = 100;
    when(user.getName()).thenReturn("test");
    when(userService.getById(id)).thenReturn(user);
    try {
      assertTrue(usersController.view(id, model).equals(UsersController.VIEW_PATH));
    } catch (PageNotFoundException e) {
      fail("Exception must not be thrown!");
    }
    when(userService.getById(id)).thenReturn(null);
    try {
      usersController.view(id, model);
      fail("PageNotFoundException must be thrown!");
    } catch (PageNotFoundException e) {
    }
    when(userService.getById(id)).thenReturn(user);
    when(user.getName()).thenReturn(rootname);
    try {
      usersController.view(id, model);
      fail("PageNotFoundException must be thrown!");
    } catch (PageNotFoundException e) {
    }
  }

  @Test
  public void testUpdate() {
    when(result.hasErrors()).thenReturn(true);
    Integer id = 100;
    String resultUpdate = usersController.update(user, result, id, true, true);
    assertTrue(resultUpdate.equals(UsersController.VIEW_PATH));
    when(result.hasErrors()).thenReturn(false);
    when(userService.getById(id)).thenReturn(user);

    resultUpdate = usersController.update(user, result, id, true, true);
    assertTrue(resultUpdate.equals(((PathSelectorTest) pathSelector).pathIfOk));
  }

  @Test
  public void testNewUser() {
    System.out.println("newUser");
    assertTrue(usersController.newUser(model).equals(UsersController.NEW_PATH));
  }

  @Test
  public void testSave() throws Throwable {
    System.out.println("save");
    when(result.hasErrors()).thenReturn(true);
    assertTrue(usersController.save(user, result, true).equals(UsersController.NEW_PATH));
    when(result.hasErrors()).thenReturn(false);
    assertTrue(usersController.save(user, result, true).equals(UsersController.REDIRECT_PATH));
  }

  @Test
  public void testDeleteUser() {
    System.out.println("deleteUser");
    assertTrue(usersController.deleteUser(1).equals("redirect:/users"));
  }

}
