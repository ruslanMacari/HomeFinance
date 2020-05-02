package homefinance.user;

import static homefinance.user.UsersController.NEW_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.common.CommonController;
import homefinance.common.exception.PageNotFoundException;
import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import org.assertj.core.api.BDDAssertions;
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
  public void openList_shouldReturnUserListTemplate() {
    BDDAssertions.then(this.usersController.openList(this.model)).isEqualTo("users/list");
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsRoot_thenExpectPageNotFoundException() {
    // given:
    given(this.userService.getById(150)).willReturn(this.user);
    given(this.user.getName()).willReturn(this.rootname);
    // when:
    this.usersController.openView(150, this.model);
  }

  @Test
  public void openView_givenUserIsNotRoot_thenOpenView() {
    // given:
    given(this.user.getName()).willReturn("test");
    given(this.userService.getById(100)).willReturn(this.user);
    // when:
    String actual = this.usersController.openView(100, this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("users/view");
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsNotNull_thenExpectPageNotFoundException() {
    // given:
    given(this.userService.getById(100)).willReturn(null);
    // when:
    this.usersController.openView(100, this.model);
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
    Assert.assertEquals(
        this.usersController.save(this.user, this.result, true, redirectAttributes, null),
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
