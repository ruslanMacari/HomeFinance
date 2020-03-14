package homefinance.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.security.User;
import homefinance.service.UserService;
import homefinance.util.PathSelector;
import homefinance.util.impl.PathSelectorTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LoginControllerTest {

  private LoginController loginController;
  private UserService userService;
  private User user;
  private BindingResult bindingResult;
  private Model model;
  private PasswordEncoder encoder;

  @Before
  public void setUp() {
    model = mock(Model.class);
    loginController = new LoginController();
    userService = mock(UserService.class);
    loginController.setUserService(userService);
    user = mock(User.class);
    bindingResult = mock(BindingResult.class);
    encoder = mock(PasswordEncoder.class);
    loginController.setEncoder(encoder);
  }

  @Test
  public void testAuthorization() {
    SecurityContext contextBefore = SecurityContextHolder.getContext();
    Authentication auth = getMockAuth();
    when(auth.isAuthenticated()).thenReturn(true);
    assertTrue(loginController.login(model).equals(LoginController.REDIRECT_ROOT));
    when(auth.isAuthenticated()).thenReturn(false);
    assertTrue(loginController.login(model).equals(LoginController.URL_PATH));
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
    assertTrue(loginController.registration(model).equals(LoginController.REGISTRATION_PATH));
    verify(model, times(1)).addAttribute("user", new User());
    Map<String, Object> map = new HashMap<>();
    map.put("model", model);
    when(model.asMap()).thenReturn(map);
    loginController.registration(model);
    verify(model, times(1)).mergeAttributes(map);
  }

  @Test
  public void testRegistrationPost() {
    when(bindingResult.hasErrors()).thenReturn(true);
    RedirectAttributes attributes = mock(RedirectAttributes.class);
    String result = loginController.registration(user, bindingResult, attributes, model);
    assertTrue(result.equals(LoginController.REDIRECT_REGISTRATION));
    when(bindingResult.hasErrors()).thenReturn(false);

    PathSelector pathSelector = new PathSelectorTest();
    loginController.setPathSelector(pathSelector);
    result = loginController.registration(user, bindingResult, attributes, model);
    assertEquals(result, ((PathSelectorTest) pathSelector).pathIfOk);
  }

}
