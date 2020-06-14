package homefinance.user.login;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.common.CommonController;
import homefinance.user.service.UserService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

  private LoginController loginController;
  @Mock
  private UserLoginDto userMock;
  @Mock
  private BindingResult bindingResultMock;
  @Mock
  private Model modelMock;
  @Mock
  private LoginFacade loginFacadeMock;
  @Mock
  private RedirectAttributes redirectAttributesMock;

  @Before
  public void setUp() {
    loginController = new LoginController(mock(UserService.class), loginFacadeMock);
  }

  @Test
  public void login_givenIsAuthenticated_thenRedirectToHome() {
    // given:
    given(loginFacadeMock.isAuthenticated()).willReturn(true);
    // when:
    String actual = loginController.login(modelMock);
    // then:
    then(actual).isEqualTo("redirect:/");
  }

  @Test
  public void login_givenIsNotAuthenticated_thenReturnLogin() {
    // given:
    given(loginFacadeMock.isAuthenticated()).willReturn(false);
    List<String> userNames = Arrays.asList("user1", "user2");
    given(loginFacadeMock.getSimpleUsersNames()).willReturn(userNames);
    // when:
    String actual = loginController.login(modelMock);
    // then:
    then(actual).isEqualTo("auth/login");
    BDDMockito.then(modelMock).should().addAttribute("userNames", userNames);
  }

  @Test
  public void openRegistration_givenModelHasNoFlashModel_thenAddAttribute() {
    // given:
    given(modelMock.asMap()).willReturn(new HashMap<>());
    // when:
    String actual = loginController.openRegistration(modelMock);
    // then:
    then(actual).isEqualTo("auth/registration");
    BDDMockito.then(modelMock).should().addAttribute(eq("user"), refEq(new UserLoginDto()));
  }

  @Test
  public void openRegistration_givenModelHasFlashModel_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(modelMock.asMap()).willReturn(map);
    // when:
    String actual = loginController.openRegistration(modelMock);
    // then:
    then(actual).isEqualTo("auth/registration");
    BDDMockito.then(modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void registerUser_givenValidationHasErrors_thenReturnRedirectToRegistration() {
    // given:
    given(bindingResultMock.hasErrors()).willReturn(true);
    // when:
    String result = loginController.registerUser(userMock, bindingResultMock, redirectAttributesMock, modelMock);
    // then:
    then(result).isEqualTo("redirect:/login/registration");
    BDDMockito.then(redirectAttributesMock).should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, modelMock);
  }

  @Test
  public void registerUser_givenValidationHasNoErrors_returnRedirectUrl() {
    // given:
    given(bindingResultMock.hasErrors()).willReturn(false);
    // when:
    String result = loginController.registerUser(userMock, bindingResultMock, redirectAttributesMock, modelMock);
    // then:
    then(result).isEqualTo(CommonController.getRedirectURL(LoginController.URL));
  }

}
