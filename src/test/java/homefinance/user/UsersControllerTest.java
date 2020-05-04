package homefinance.user;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.exception.PageNotFoundException;
import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.mockito.BDDMockito;
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
  public void openView_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.usersController.openView(10, this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("users/view");
    BDDMockito.then(this.model).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateIsOk_thenRedirectToUsersUrl() {
    // given:
    given(this.result.hasErrors()).willReturn(false);
    given(this.userService.getById(100)).willReturn(this.user);
    // when:
    String actual = this.usersController.update(this.user, this.result, 100, true, true,
        mock(RedirectAttributes.class), this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateThrownException_thenRedirectToUsersViewUrl() {
    // given:
    given(this.result.hasErrors()).willReturn(false);
    given(this.userService.getById(100)).willReturn(this.user);
    doThrow(DuplicateFieldsException.class).when(this.userService).update(this.user);
    // when:
    String actual = this.usersController.update(this.user, this.result, 100, true, true,
        mock(RedirectAttributes.class), this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users/" + 100);
  }

  @Test
  public void update_givenResultHasErrors_thenRedirectToView() {
    this.update_givenResultHasErrorsAndId_thenRedirectToViewId(100);
    this.update_givenResultHasErrorsAndId_thenRedirectToViewId(50);
  }

  private void update_givenResultHasErrorsAndId_thenRedirectToViewId(int id) {
    // given:
    given(this.result.hasErrors()).willReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actual = this.usersController
        .update(this.user, this.result, id, true, true, redirectAttributes, this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users/" + id);
    BDDMockito.then(redirectAttributes)
        .should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, this.model);
  }

  @Test
  public void testNewUser() {
    assertThat(this.usersController.newUser(this.model)).isEqualTo("users/new");
  }

  @Test
  public void testSave() {
    // TODO: 04.05.2020: split in 2 tests, refactor by BDD
    when(this.result.hasErrors()).thenReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    String actual = this.usersController
        .save(this.user, this.result, true, redirectAttributes, null);
    assertThat(actual).isEqualTo("redirect:/users/new");
    when(this.result.hasErrors()).thenReturn(false);
    actual = this.usersController.save(this.user, this.result, true, redirectAttributes, null);
    assertThat(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void testDeleteUser() {
    assertEquals("redirect:/users", this.usersController.deleteUser(1));
  }

}
