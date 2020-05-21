package homefinance.user.login;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Before
  public void setUp() {
    this.loginController = new LoginController(mock(UserService.class));
  }

  @Test
  public void testAuthorization() {
    SecurityContext contextBefore = SecurityContextHolder.getContext();
    Authentication auth = this.getMockAuth();
    when(auth.isAuthenticated()).thenReturn(true);
    assertThat(this.loginController.login(this.modelMock), is(LoginController.REDIRECT_ROOT));
    when(auth.isAuthenticated()).thenReturn(false);
    assertThat(this.loginController.login(this.modelMock), is(LoginController.URL_PATH));
    SecurityContextHolder.setContext(contextBefore);
  }

  private Authentication getMockAuth() {
    SecurityContext context = mock(SecurityContext.class);
    SecurityContextHolder.setContext(context);
    Authentication auth = mock(Authentication.class);
    when(context.getAuthentication()).thenReturn(auth);
    return auth;
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
