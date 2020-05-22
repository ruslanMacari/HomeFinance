package homefinance.user.login;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.common.util.impl.PathSelectorTest;
import homefinance.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

  @Before
  public void setUp() {
    this.loginController = new LoginController(mock(UserService.class), this.loginFacadeMock);
  }

  @Test
  public void login_givenIsAuthenticated_thenRedirectToHome() {
    // given:
    given(this.loginFacadeMock.isAuthenticated()).willReturn(true);
    // when:
    String actual = this.loginController.login(this.modelMock);
    // then:
    then(actual).isEqualTo("redirect:/");
  }

  @Test
  public void login_givenIsNotAuthenticated_thenReturnLogin() {
    // given:
    given(this.loginFacadeMock.isAuthenticated()).willReturn(false);
    // when:
    String actual = this.loginController.login(this.modelMock);
    // then:
    then(actual).isEqualTo("auth/login");
  }

  @Test
  public void testRegistrationGet() {
    assertThat(this.loginController.registration(this.modelMock),
        is(LoginController.REGISTRATION_PATH));
    verify(this.modelMock, times(1)).addAttribute("user", new UserLoginDto());
    Map<String, Object> map = new HashMap<>();
    map.put("model", this.modelMock);
    when(this.modelMock.asMap()).thenReturn(map);
    this.loginController.registration(this.modelMock);
    verify(this.modelMock, times(1)).mergeAttributes(map);
  }

  @Test
  public void testRegistrationPost() {
    when(this.bindingResultMock.hasErrors()).thenReturn(true);
    RedirectAttributes attributes = mock(RedirectAttributes.class);
    String result = this.loginController
        .registration(this.userMock, this.bindingResultMock, attributes, this.modelMock);
    assertThat(result, is(LoginController.REDIRECT_REGISTRATION));
    when(this.bindingResultMock.hasErrors()).thenReturn(false);

    PathSelectorTest pathSelector = new PathSelectorTest();
    this.loginController.setPathSelector(pathSelector);
    result = this.loginController
        .registration(this.userMock, this.bindingResultMock, attributes, this.modelMock);
    assertThat(result, is(pathSelector.pathIfOk));
  }

}
