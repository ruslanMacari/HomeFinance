package homefinance.user;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.exception.PageNotFoundException;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UsersControllerTest {

  private final String rootname = "root";
  private UsersController usersController;
  @Mock
  private Model modelMock;
  @Mock
  private UserService userServiceMock;
  @Mock
  private PasswordEncoder encoderMock;
  @Mock
  private BindingResult resultMock;
  @Mock
  private User userMock;
  @Mock
  private UserDto userDtoMock;
  @Mock
  private UserFacade userFacadeMock;
  @Mock
  private Principal principalMock;

  @Before
  public void setUp() {
    usersController = new UsersController(userServiceMock, encoderMock, userFacadeMock);
    usersController.setRootname(rootname);
    usersController.setPathSelector(new PathSelectorTest());
  }

  @Test
  public void openList_givenUsersWithoutRoot_shouldReturnUserListTemplateAndAddUsersWithoutRoot() {
    // given:
    List<UserDto> usersDto = Collections.singletonList(mock(UserDto.class));
    given(userFacadeMock.getUsersWithoutRoot()).willReturn(usersDto);
    // when:
    String actual = usersController.openList(modelMock);
    // then:
    then(actual).isEqualTo("users/list");
    BDDMockito.then(modelMock).should().addAttribute("users", usersDto);
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsRoot_thenExpectPageNotFoundException() {
    // given:
    given(userFacadeMock.getUserById(150)).willReturn(userDtoMock);
    given(userDtoMock.getName()).willReturn(rootname);
    // when:
    usersController.openView(150, modelMock);
  }

  @Test
  public void openView_givenUserIsNotRoot_thenOpenView() {
    // given:
    given(userDtoMock.getName()).willReturn("test");
    given(userFacadeMock.getUserById(100)).willReturn(userDtoMock);
    // when:
    String actual = usersController.openView(100, modelMock);
    // then:
    then(actual).isEqualTo("users/view");
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsNotNull_thenExpectPageNotFoundException() {
    // given:
    given(userFacadeMock.getUserById(100)).willReturn(null);
    // when:
    usersController.openView(100, modelMock);
  }

  @Test
  public void openView_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(modelMock.asMap()).willReturn(map);
    // when:
    String actual = usersController.openView(10, modelMock);
    // then:
    then(actual).isEqualTo("users/view");
    BDDMockito.then(modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateIsOk_thenRedirectToUsersUrl() {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    given(userDtoMock.getId()).willReturn(100);
    given(userServiceMock.getById(100)).willReturn(userMock);
    // when:
    String actual = usersController.update(userDtoMock, resultMock, mock(RedirectAttributes.class), modelMock);
    // then:
    then(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateThrownException_thenRedirectToUsersViewUrl() {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    given(userDtoMock.getId()).willReturn(100);
    given(userServiceMock.getById(100)).willReturn(userMock);
    doThrow(DuplicateFieldsException.class).when(userServiceMock).update(userMock);
    // when:
    String actual = usersController.update(userDtoMock, resultMock, mock(RedirectAttributes.class), modelMock);
    // then:
    then(actual).isEqualTo("redirect:/users/" + 100);
  }

  @Test
  public void update_givenResultHasErrors_thenRedirectToView() {
    update_givenResultHasErrorsAndId_thenRedirectToViewId(100);
    update_givenResultHasErrorsAndId_thenRedirectToViewId(50);
  }

  private void update_givenResultHasErrorsAndId_thenRedirectToViewId(int id) {
    // given:
    given(resultMock.hasErrors()).willReturn(true);
    given(userDtoMock.getId()).willReturn(id);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actual = usersController.update(userDtoMock, resultMock, redirectAttributes, modelMock);
    // then:
    then(actual).isEqualTo("redirect:/users/" + id);
    BDDMockito.then(redirectAttributes).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, modelMock);
  }

  @Test
  public void newUser_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(modelMock.asMap()).willReturn(map);
    // when:
    String actual = usersController.newUser(modelMock);
    // then:
    then(actual).isEqualTo("users/new");
    BDDMockito.then(modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void newUser_givenIsNotRedirect_thenAddUserToModel() {
    // when:
    String actual = usersController.newUser(modelMock);
    // then:
    then(actual).isEqualTo("users/new");
    BDDMockito.then(modelMock).should().addAttribute(eq("user"), refEq(new User()));
  }

  @Test
  public void save_givenResultHasNoErrors_thenRedirectToUsers() {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    // when:
    String actual = usersController.save(userMock, resultMock, true, mock(RedirectAttributes.class), null);
    // then:
    then(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void save_givenResultHasErrors_thenRedirectToNew() {
    // given:
    given(resultMock.hasErrors()).willReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actual = usersController.save(userMock, resultMock, true, redirectAttributes, null);
    // then:
    then(actual).isEqualTo("redirect:/users/new");
    BDDMockito.then(redirectAttributes).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, null);
  }

  @Test
  public void deleteUser_givenUserPrincipalIsNotUserToDelete_deleteUser() {
    // given:
    given(principalMock.getName()).willReturn("root user");
    given(userDtoMock.getName()).willReturn("test user");
    given(userDtoMock.getId()).willReturn(16);

    // when:
    String actual = usersController.deleteUser(userDtoMock, principalMock);
    // then:
    then(actual).isEqualTo("redirect:/users");
    BDDMockito.then(userFacadeMock).should().deleteUser(16);
  }

  @Test
  public void deleteUser_givenUserPrincipalIsUserToDelete_thenRefuseDeleteUser() {
    // given:
    Principal userPrincipal = mock(Principal.class);
    given(userPrincipal.getName()).willReturn("root user");
    given(userDtoMock.getName()).willReturn("root user");

    // when:
    String actual = usersController.deleteUser(userDtoMock, userPrincipal);
    // then:
    then(actual).isEqualTo("redirect:/users");
    BDDMockito.then(userFacadeMock).shouldHaveZeroInteractions();
  }

}
