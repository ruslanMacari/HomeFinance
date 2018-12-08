package ruslan.macari.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.security.User;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ruslan.macari.service.UserService;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ruslan.macari.config.TestConfig;

//@DirtiesContext
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
public class LoginControllerTest {
    
    private MockHttpSession session;
    private LoginController loginController;
    private UserService userService;
    private User user;
    private BindingResult bindingResult;
    private Model model;
    
    @Before
    public void setUp() {
//        model = mock(Model.class);
//        session = new MockHttpSession();
//        loginController = new LoginController();
//        userService = mock(UserService.class);
//        loginController.setUserService(userService);
//        user = mock(User.class);
//        bindingResult = mock(BindingResult.class);
//        currentUser = mock(CurrentUser.class);
//        //loginController.setCurrentUser(currentUser);
//        loginController.setRoot(user);
    }
    
    @After
    public void cleanUp() {
    }
    
    @Test
    public void testInit() {
//        when(userService.getRoot()).thenReturn(user);
//        loginController.init();
//        verify(userService, times(0)).add(user);
//        when(userService.getRoot()).thenReturn(null);
//        loginController.init();
//        verify(userService).add(user);
    }
    
    @Test
    public void testAuthorization() {
//        when(currentUser.exists(session.getId())).thenReturn(false);
//        assertEquals(loginController.authorization(session, model), "login/authorization");
//        session = mock(MockHttpSession.class);
//        when(currentUser.exists(session.getId())).thenReturn(true);
//        assertEquals(loginController.authorization(session, model), "redirect:/");
    }

//    @Test
//    public void testCreateUser() {
//        ModelAndView modelAndView = loginController.createUser();
//        assertTrue(modelAndView.getViewName().equals("login/createUser"));
//        ModelMap map = modelAndView.getModelMap();
//        assertTrue(map.containsAttribute("user"));
//        assertEquals(map.get("user"), new User());
//    }
//
//    @Test
//    public void testSaveUser() {
//        when(bindingResult.hasErrors()).thenReturn(true);
//        String result = loginController.saveUser(user, bindingResult);
//        assertEquals(result, "login/createUser");
//        verifyZeroInteractions(userService);
//        when(bindingResult.hasErrors()).thenReturn(false);
//        result = loginController.saveUser(user, bindingResult);
//        assertEquals(result, "redirect:/authorization");
//        verify(userService).add(user);
//    }

    @Test
    public void testPostAuthorization() {
//        when(bindingResult.hasErrors()).thenReturn(true);
//        String result = loginController.authorization(user, bindingResult, model, session);
//        verify(model, times(1)).addAttribute("listUsers", userService.getSimpleUsers());
//        assertEquals(result, "login/authorization");
//        when(bindingResult.hasErrors()).thenReturn(false);
//        when(userService.getByNameAndPassword(user.getName(), user.getPassword())).thenReturn(user);
//        result = loginController.authorization(user, bindingResult, model, session);
//        assertEquals(result, "redirect:/");
//        assertEquals(session.getAttribute("user"), user);
    }
//
//    @Test
//    public void testCheckUser() {
//        UserLogin userLogin = mock(UserLogin.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//        Model model = mock(Model.class);
//        String result = loginController.checkUser(userLogin, bindingResult, model, session);
//        assertEquals(result, "login");
//        when(bindingResult.hasErrors()).thenReturn(false);
//        result = loginController.checkUser(userLogin, bindingResult, model, session);
//        assertEquals(result, "redirect:/home");
//    }
//    
//    @Test
//    public void testLogout() {
//        session = new MockHttpSession();
//        session.setAttribute("user", new User());
//        String result = loginController.logout(session);
//        assertEquals(result, "redirect:/login");
//        assertNull(session.getAttribute("user"));
//    }
    
}
