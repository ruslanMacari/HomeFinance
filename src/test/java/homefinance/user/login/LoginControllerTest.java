package homefinance.user.login;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.user.service.UserService;
import homefinance.user.login.UserLoginDto;
import homefinance.common.util.impl.PathSelectorTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LoginControllerTest {

  private LoginController loginController;
  private UserLoginDto user;
  private BindingResult bindingResult;
  private Model model;

  @Before
  public void setUp() {
    this.model = mock(Model.class);
    this.loginController = new LoginController();
    UserService userService = mock(UserService.class);
    this.loginController.setUserService(userService);
    this.user = mock(UserLoginDto.class);
    this.bindingResult = mock(BindingResult.class);
  }

  @Test
  public void testAuthorization() {
    SecurityContext contextBefore = SecurityContextHolder.getContext();
    Authentication auth = this.getMockAuth();
    when(auth.isAuthenticated()).thenReturn(true);
    assertThat(this.loginController.login(this.model), is(LoginController.REDIRECT_ROOT));
    when(auth.isAuthenticated()).thenReturn(false);
    assertThat(this.loginController.login(this.model), is(LoginController.URL_PATH));
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
    assertThat(this.loginController.registration(this.model),
        is(LoginController.REGISTRATION_PATH));
    verify(this.model, times(1)).addAttribute("user", new UserLoginDto());
    Map<String, Object> map = new HashMap<>();
    map.put("model", this.model);
    when(this.model.asMap()).thenReturn(map);
    this.loginController.registration(this.model);
    verify(this.model, times(1)).mergeAttributes(map);
  }

  @Test
  public void testRegistrationPost() {
    when(this.bindingResult.hasErrors()).thenReturn(true);
    RedirectAttributes attributes = mock(RedirectAttributes.class);
    String result = this.loginController
        .registration(this.user, this.bindingResult, attributes, this.model);
    assertThat(result, is(LoginController.REDIRECT_REGISTRATION));
    when(this.bindingResult.hasErrors()).thenReturn(false);

    PathSelectorTest pathSelector = new PathSelectorTest();
    this.loginController.setPathSelector(pathSelector);
    result = this.loginController
        .registration(this.user, this.bindingResult, attributes, this.model);
    assertThat(result, is(pathSelector.pathIfOk));
  }

}
