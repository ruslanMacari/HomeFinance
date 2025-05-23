package homefinance.user;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.common.PossibleDuplicationException;
import homefinance.common.PossibleDuplicationExceptionViewNameInRequestBuffer;
import homefinance.common.RequestBuffer;
import homefinance.common.exception.PageNotFoundException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  private UserController userController;
  @Mock private Model modelMock;
  @Mock private BindingResult resultMock;
  @Mock private UserDto userDtoMock;//TODO: remove mock
  @Mock private UserFacade userFacadeMock;
  @Mock private Principal principalMock;
  @Mock private RedirectAttributes redirectAttributesMock;
  @Mock private RequestBuffer requestBufferMock;

  @BeforeEach
  void setUp() {
    userController = new UserController(userFacadeMock, requestBufferMock);
  }

  @Test
  void openList_givenUsersWithoutRoot_shouldReturnUserListTemplateAndAddUsersWithoutRoot() {
    // given:
    List<UserDto> usersDto = Collections.singletonList(mock(UserDto.class));
    given(userFacadeMock.getUsersWithoutRoot()).willReturn(usersDto);
    // when:
    String actual = userController.openList(modelMock);
    // then:
    then(actual).isEqualTo("users/list");
    BDDMockito.then(modelMock).should().addAttribute("users", usersDto);
  }

  @Test()
  void openView_givenUserIsRoot_thenExpectPageNotFoundException() {
    // given:
    given(userFacadeMock.getUserById(150L)).willReturn(userDtoMock);
    given(userFacadeMock.isRoot(userDtoMock)).willReturn(true);
    // when:
    assertThrows(PageNotFoundException.class, () -> userController.openView(150L, modelMock));
  }

  @Test
  void openView_givenUserIsNotRoot_thenOpenView() {
    // given:
    given(userFacadeMock.getUserById(100L)).willReturn(userDtoMock);
    given(userFacadeMock.isRoot(userDtoMock)).willReturn(false);
    // when:
    String actual = userController.openView(100L, modelMock);
    // then:
    then(actual).isEqualTo("users/view");
  }

  @Test()
  void openView_givenUserIsNotNull_thenExpectPageNotFoundException() {
    // given:
    given(userFacadeMock.getUserById(100L)).willReturn(null);
    // when:
    assertThrows(PageNotFoundException.class, () -> userController.openView(100L, modelMock));
  }

  @Test
  void openView_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(modelMock.asMap()).willReturn(map);
    // when:
    String actual = userController.openView(10L, modelMock);
    // then:
    then(actual).isEqualTo("users/view");
    BDDMockito.then(modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  void update_givenResultHasNoErrorsAndUpdateIsOk_thenRedirectToUsersUrl() throws NoSuchMethodException {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    // when:
    String actual = userController.update(userDto, resultMock, redirectAttributesMock, modelMock);
    // then:
    Method update = userController.getClass().getMethod("update", UserDto.class, BindingResult.class,
        RedirectAttributes.class, Model.class);
    then(update.getAnnotation(PossibleDuplicationExceptionViewNameInRequestBuffer.class)).isNotNull();
    then(actual).isEqualTo("redirect:/users");
    InOrder inOrder = BDDMockito.inOrder(requestBufferMock, userFacadeMock);
    BDDMockito.then(requestBufferMock).should(inOrder).setViewName(UserController.URL + "/1");
    BDDMockito.then(userFacadeMock).should(inOrder).update(userDto);
  }

  @Test
  void update_givenResultHasErrors_thenRedirectToView() {
    // given:
    for (long id : new long[] {100, 50}) {
      given(resultMock.hasErrors()).willReturn(true);
      given(userDtoMock.getId()).willReturn(id);
      RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
      // when:
      String actual = userController.update(userDtoMock, resultMock, redirectAttributes, modelMock);
      // then:
      then(actual).as("for id " + id + " result must be:").isEqualTo("redirect:/users/" + id);
      BDDMockito.then(redirectAttributes).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, modelMock);
    }
  }

  @Test
  void newUser_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(modelMock.asMap()).willReturn(map);
    // when:
    String actual = userController.newUser(modelMock);
    // then:
    then(actual).isEqualTo("users/new");
    BDDMockito.then(modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  void newUser_givenIsNotRedirect_thenAddUserToModel() {
    // when:
    String actual = userController.newUser(modelMock);
    // then:
    then(actual).isEqualTo("users/new");
    BDDMockito.then(modelMock).should().addAttribute(eq("user"), refEq(new UserDto()));
  }

  @Test
  void save_givenResultHasNoErrors_thenRedirectToUsers() throws NoSuchMethodException {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    // when:
    String viewName = userController.save(userDtoMock, resultMock, mock(RedirectAttributes.class), null);
    // then:
    Method save = userController.getClass().getMethod("save", UserDto.class, BindingResult.class,
        RedirectAttributes.class, Model.class);
    then(save.getAnnotation(PossibleDuplicationException.class)).isNotNull();
    then(save.getAnnotation(PossibleDuplicationException.class).viewName()).isEqualTo("/users/new");
    BDDMockito.then(userFacadeMock).should().addUser(userDtoMock);
    then(viewName).isEqualTo("redirect:/users");
  }

  @Test
  void save_givenResultHasErrors_thenRedirectToNew() {
    // given:
    given(resultMock.hasErrors()).willReturn(true);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actual = userController.save(userDtoMock, resultMock, redirectAttributes, null);
    // then:
    then(actual).isEqualTo("redirect:/users/new");
    BDDMockito.then(redirectAttributes).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, null);
  }

  @Test
  void deleteUser_givenUserPrincipalIsNotUserToDelete_deleteUser() {
    // given:
    given(principalMock.getName()).willReturn("root user");
    given(userDtoMock.getName()).willReturn("test user");
    given(userDtoMock.getId()).willReturn(16L);

    // when:
    String actual = userController.deleteUser(userDtoMock, principalMock);
    // then:
    then(actual).isEqualTo("redirect:/users");
    BDDMockito.then(userFacadeMock).should().deleteUser(16L);
  }

  @Test()
  void deleteUser_givenUserPrincipalIsUserToDelete_thenRefuseDeleteUser() {
    // given:
    Principal userPrincipal = mock(Principal.class);
    given(userPrincipal.getName()).willReturn("root user");
    given(userDtoMock.getName()).willReturn("root user");

    // when:
    assertThrows(RuntimeException.class, () -> userController.deleteUser(userDtoMock, userPrincipal));
  }

}
