package homefinance.user;

import static homefinance.user.UsersController.NEW_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.common.exception.PageNotFoundException;
import homefinance.common.CommonController;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    this.userService = mock(UserService.class);
    this.encoder = mock(PasswordEncoder.class);
    this.rootname = "root";
    this.validator = mock(Validator.class);
    this.usersController = new UsersController();
    this.usersController.setUserService(this.userService);
    this.usersController.setEncoder(this.encoder);
    this.usersController.setRootname(this.rootname);
    this.model = mock(Model.class);
    this.result = mock(BindingResult.class);
    this.user = mock(User.class);
    this.pathSelector = new PathSelectorTest();
    this.usersController.setPathSelector(this.pathSelector);
  }

  @Test
  public void testList() {
    assertEquals(this.usersController.list(this.model), UsersController.LIST_PATH);
  }

  @Test
  public void testView() {
    System.out.println("view");
    Integer id = 100;
    when(this.user.getName()).thenReturn("test");
    when(this.userService.getById(id)).thenReturn(this.user);
    try {
      assertTrue(this.usersController.view(id, this.model).equals(UsersController.VIEW_PATH));
    } catch (PageNotFoundException e) {
      fail("Exception must not be thrown!");
    }
    when(this.userService.getById(id)).thenReturn(null);
    try {
      this.usersController.view(id, this.model);
      fail("PageNotFoundException must be thrown!");
    } catch (PageNotFoundException e) {
    }
    when(this.userService.getById(id)).thenReturn(this.user);
    when(this.user.getName()).thenReturn(this.rootname);
    try {
      this.usersController.view(id, this.model);
      fail("PageNotFoundException must be thrown!");
    } catch (PageNotFoundException e) {
    }
  }

  @Test
  public void testUpdate() {
    when(this.result.hasErrors()).thenReturn(true);
    Integer id = 100;
    String resultUpdate = this.usersController.update(this.user, this.result, id, true, true);
    assertTrue(resultUpdate.equals(UsersController.VIEW_PATH));
    when(this.result.hasErrors()).thenReturn(false);
    when(this.userService.getById(id)).thenReturn(this.user);

    resultUpdate = this.usersController.update(this.user, this.result, id, true, true);
    assertTrue(resultUpdate.equals(((PathSelectorTest) this.pathSelector).pathIfOk));
  }

  @Test
  public void testNewUser() {
    assertEquals(this.usersController.newUser(this.model), NEW_PATH);
  }

  @Test
  public void testSave() {
    when(this.result.hasErrors()).thenReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    Assert.assertEquals(this.usersController.save(this.user, this.result, true, redirectAttributes, null),
        CommonController.getRedirectURL(NEW_PATH));
    when(this.result.hasErrors()).thenReturn(false);
    assertEquals(this.usersController.save(this.user, this.result, true, redirectAttributes, null),
        UsersController.REDIRECT_PATH);
  }

  @Test
  public void testDeleteUser() {
    assertEquals("redirect:/users", this.usersController.deleteUser(1));
  }

}
