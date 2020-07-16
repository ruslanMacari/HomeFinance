package homefinance.user;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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

  private final UsersController usersController;
  private final Model model;
  private final UserService userService;
  private final PasswordEncoder encoder;
  private final String rootname;
  private final BindingResult result;
  private final User user;
  private final Validator validator;
  private final PathSelector pathSelector;

  public UsersControllerTest() {
    userService = mock(UserService.class);
    encoder = mock(PasswordEncoder.class);
    rootname = "root";
    validator = mock(Validator.class);
    usersController = new UsersController(userService, encoder);
    usersController.setRootname(rootname);
    model = mock(Model.class);
    result = mock(BindingResult.class);
    user = mock(User.class);
    pathSelector = new PathSelectorTest();
    usersController.setPathSelector(pathSelector);
  }

  @Test
  public void openList_shouldReturnUserListTemplate() {
    BDDAssertions.then(usersController.openList(model)).isEqualTo("users/list");
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsRoot_thenExpectPageNotFoundException() {
    // given:
    given(userService.getById(150)).willReturn(user);
    given(user.getName()).willReturn(rootname);
    // when:
    usersController.openView(150, model);
  }

  @Test
  public void openView_givenUserIsNotRoot_thenOpenView() {
    // given:
    given(user.getName()).willReturn("test");
    given(userService.getById(100)).willReturn(user);
    // when:
    String actual = usersController.openView(100, model);
    // then:
    BDDAssertions.then(actual).isEqualTo("users/view");
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsNotNull_thenExpectPageNotFoundException() {
    // given:
    given(userService.getById(100)).willReturn(null);
    // when:
    usersController.openView(100, model);
  }

  @Test
  public void openView_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(model.asMap()).willReturn(map);
    // when:
    String actual = usersController.openView(10, model);
    // then:
    BDDAssertions.then(actual).isEqualTo("users/view");
    BDDMockito.then(model).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateIsOk_thenRedirectToUsersUrl() {
    // given:
    given(result.hasErrors()).willReturn(false);
    given(userService.getById(100)).willReturn(user);
    // when:
    String actual = usersController.update(user, result, 100, true, true, mock(RedirectAttributes.class), model);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateThrownException_thenRedirectToUsersViewUrl() {
    // given:
    given(result.hasErrors()).willReturn(false);
    given(userService.getById(100)).willReturn(user);
    doThrow(DuplicateFieldsException.class).when(userService).update(user);
    // when:
    String actual = usersController.update(user, result, 100, true, true, mock(RedirectAttributes.class), model);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users/" + 100);
  }

  @Test
  public void update_givenResultHasErrors_thenRedirectToView() {
    update_givenResultHasErrorsAndId_thenRedirectToViewId(100);
    update_givenResultHasErrorsAndId_thenRedirectToViewId(50);
  }

  private void update_givenResultHasErrorsAndId_thenRedirectToViewId(int id) {
    // given:
    given(result.hasErrors()).willReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actual = usersController.update(user, result, id, true, true, redirectAttributes, model);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users/" + id);
    BDDMockito.then(redirectAttributes).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, model);
  }

  @Test
  public void newUser_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(model.asMap()).willReturn(map);
    // when:
    String actual = usersController.newUser(model);
    // then:
    BDDAssertions.then(actual).isEqualTo("users/new");
    BDDMockito.then(model).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void newUser_givenIsNotRedirect_thenAddUserToModel() {
    // when:
    String actual = usersController.newUser(model);
    // then:
    BDDAssertions.then(actual).isEqualTo("users/new");
    BDDMockito.then(model).should().addAttribute(eq("user"), refEq(new User()));
  }

  @Test
  public void save_givenResultHasNoErrors_thenRedirectToUsers() {
    // given:
    given(result.hasErrors()).willReturn(false);
    // when:
    String actual = usersController.save(user, result, true, mock(RedirectAttributes.class), null);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void save_givenResultHasErrors_thenRedirectToNew() {
    // given:
    given(result.hasErrors()).willReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actual = usersController.save(user, result, true, redirectAttributes, null);
    // then:
    BDDAssertions.then(actual).isEqualTo("redirect:/users/new");
    BDDMockito.then(redirectAttributes).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, null);
  }

  @Test
  public void testDeleteUser() {
    assertEquals("redirect:/users", usersController.deleteUser(1));
  }

}
