package homefinance.user;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.common.PossibleDuplicationExceptionViewNameInRequestBuffer;
import homefinance.common.RequestBuffer;
import homefinance.common.exception.PageNotFoundException;
import homefinance.common.util.impl.PathSelectorTest;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  private final String rootname = "root";
  private UserController userController;
  @Mock private Model modelMock;
  @Mock private BindingResult resultMock;
  @Mock private UserDto userDtoMock;
  @Mock private UserFacade userFacadeMock;
  @Mock private Principal principalMock;
  @Mock private RedirectAttributes redirectAttributesMock;
  @Mock private RequestBuffer requestBufferMock;

  @Before
  public void setUp() {
    userController = new UserController(userFacadeMock, requestBufferMock);
    userController.setRootname(rootname);
    userController.setPathSelector(new PathSelectorTest());
  }

  @Test
  public void openList_givenUsersWithoutRoot_shouldReturnUserListTemplateAndAddUsersWithoutRoot() {
    // given:
    List<UserDto> usersDto = Collections.singletonList(mock(UserDto.class));
    given(userFacadeMock.getUsersWithoutRoot()).willReturn(usersDto);
    // when:
    String actual = userController.openList(modelMock);
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
    userController.openView(150, modelMock);
  }

  @Test
  public void openView_givenUserIsNotRoot_thenOpenView() {
    // given:
    given(userDtoMock.getName()).willReturn("test");
    given(userFacadeMock.getUserById(100)).willReturn(userDtoMock);
    // when:
    String actual = userController.openView(100, modelMock);
    // then:
    then(actual).isEqualTo("users/view");
  }

  @Test(expected = PageNotFoundException.class)
  public void openView_givenUserIsNotNull_thenExpectPageNotFoundException() {
    // given:
    given(userFacadeMock.getUserById(100)).willReturn(null);
    // when:
    userController.openView(100, modelMock);
  }

  @Test
  public void openView_givenIsRedirect_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(modelMock.asMap()).willReturn(map);
    // when:
    String actual = userController.openView(10, modelMock);
    // then:
    then(actual).isEqualTo("users/view");
    BDDMockito.then(modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void update_givenResultHasNoErrorsAndUpdateIsOk_thenRedirectToUsersUrl() throws NoSuchMethodException {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    UserDto userDto = new UserDto();
    userDto.setId(1);
    // when:
    String actual = userController.update(userDto, resultMock, redirectAttributesMock, modelMock);
    // then:
    Method update = userController.getClass().getMethod("update", UserDto.class, BindingResult.class,
        RedirectAttributes.class, Model.class);
    then(update.getAnnotation(PossibleDuplicationExceptionViewNameInRequestBuffer.class)).isNotNull();
    then(actual).isEqualTo("redirect:/users");
    InOrder inOrder = BDDMockito.inOrder(requestBufferMock, userFacadeMock);
    inOrder.verify(requestBufferMock).setViewName(UserController.URL + "/1");
    inOrder.verify(userFacadeMock).update(userDto);
  }

  @Test
  public void update_givenResultHasErrors_thenRedirectToView() {
    for (int id : new int[] {100, 50}) {
      // given:
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
  public void newUser_givenIsRedirect_thenMergeAttributesFromFlashModel() {
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
  public void newUser_givenIsNotRedirect_thenAddUserToModel() {
    // when:
    String actual = userController.newUser(modelMock);
    // then:
    then(actual).isEqualTo("users/new");
    BDDMockito.then(modelMock).should().addAttribute(eq("user"), refEq(new UserDto()));
  }

  @Test
  public void save_givenResultHasNoErrors_thenRedirectToUsers() {
    // given:
    given(resultMock.hasErrors()).willReturn(false);
    // when:
    String actual = userController.save(userDtoMock, resultMock, mock(RedirectAttributes.class), null);
    // then:
    then(actual).isEqualTo("redirect:/users");
  }

  @Test
  public void save_givenResultHasErrors_thenRedirectToNew() {
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
  public void deleteUser_givenUserPrincipalIsNotUserToDelete_deleteUser() {
    // given:
    given(principalMock.getName()).willReturn("root user");
    given(userDtoMock.getName()).willReturn("test user");
    given(userDtoMock.getId()).willReturn(16);

    // when:
    String actual = userController.deleteUser(userDtoMock, principalMock);
    // then:
    then(actual).isEqualTo("redirect:/users");
    BDDMockito.then(userFacadeMock).should().deleteUser(16);
  }

  @Test(expected = RuntimeException.class)
  public void deleteUser_givenUserPrincipalIsUserToDelete_thenRefuseDeleteUser() {
    // given:
    Principal userPrincipal = mock(Principal.class);
    given(userPrincipal.getName()).willReturn("root user");
    given(userDtoMock.getName()).willReturn("root user");

    // when:
    userController.deleteUser(userDtoMock, userPrincipal);
  }

}
