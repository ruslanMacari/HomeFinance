package ruslan.macari.controllers;

import javax.servlet.http.HttpSession;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.domain.User;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verify;
import org.springframework.ui.Model;
import ruslan.macari.domain.UserLogin;
import ruslan.macari.service.UserService;

public class LoginControllerTest {
    
    private MockHttpSession session;
    private LoginController loginController;
    private UserService userService;
    
    public LoginControllerTest() {
        session = new MockHttpSession();
        loginController = new LoginController();
        userService = mock(UserService.class);
        loginController.setUserService(userService);
    }
    
    @Test
    public void testMain() {
        assertEquals(loginController.main(session), "redirect:/login");
        session.setAttribute("user", new User());
        assertEquals(loginController.main(session), "redirect:/home");
    }

    @Test
    public void testCreateUser() {
        ModelAndView modelAndView = loginController.createUser();
        assertTrue(modelAndView.getViewName().equals("createUser"));
        ModelMap map = modelAndView.getModelMap();
        assertTrue(map.containsAttribute("user"));
        assertEquals(map.get("user"), new User());
    }

    @Test
    public void testSaveUser() {
        BindingResult bindingResult = mock(BindingResult.class);
        User user = mock(User.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = loginController.saveUser(user, bindingResult);
        assertEquals(result, "createUser");
        verifyZeroInteractions(userService);
        when(bindingResult.hasErrors()).thenReturn(false);
        result = loginController.saveUser(user, bindingResult);
        assertEquals(result, "redirect:/login");
        verify(userService).addUser(user);
    }

    @Test
    public void testLogin() {
        String result = loginController.login(mock(Model.class));
        assertEquals(result, "login");
    }

    @Test
    public void testCheckUser() {
        UserLogin user = mock(UserLogin.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        Model model = mock(Model.class);
        String result = loginController.checkUser(user, bindingResult, model, session);
        assertEquals(result, "login");
        when(bindingResult.hasErrors()).thenReturn(false);
        result = loginController.checkUser(user, bindingResult, model, session);
        assertEquals(result, "redirect:/home");
    }
    
}
