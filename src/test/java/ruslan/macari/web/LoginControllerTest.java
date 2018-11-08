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
import ruslan.macari.domain.User;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verify;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ruslan.macari.service.UserService;
import ruslan.macari.web.utils.CurrentUser;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ruslan.macari.config.TestConfig;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(CurrentUser.class)
public class LoginControllerTest {
    
    private MockHttpSession session;
    private LoginController loginController;
    private UserService userService;
    private User user;
    private BindingResult bindingResult;
    private Model model;
    
    public LoginControllerTest() {
        init();
    }
    
    private void init() {
        
    }
    
    @Before
    public void setUp() {
        model = new ExtendedModelMap();
        session = new MockHttpSession();
        loginController = new LoginController();
        userService = mock(UserService.class);
        loginController.setUserService(userService);
        user = mock(User.class);
        bindingResult = mock(BindingResult.class);
    }
    
    @After
    public void cleanUp() {
        CurrentUser.remove("id");
    }
    
    @Test
    public void testAuthorization() {
        assertEquals(loginController.authorization(session, model), "login/authorization");
        session = mock(MockHttpSession.class);
        when(session.getId()).thenReturn("id");
//        PowerMockito.mockStatic(CurrentUser.class);
//        BDDMockito.given(DriverManager.getConnection(...)).willReturn(...);
//
//        //when
//        sut.execute();
//
//        //then
//        PowerMockito.verifyStatic();
//        DriverManager.getConnection(...);

        CurrentUser.add("id", user);
        assertEquals(loginController.authorization(session, model), "redirect:/");
    }
//
//    @Test
//    public void testCreateUser() {
//        ModelAndView modelAndView = loginController.createUser();
//        assertTrue(modelAndView.getViewName().equals("createUser"));
//        ModelMap map = modelAndView.getModelMap();
//        assertTrue(map.containsAttribute("user"));
//        assertEquals(map.get("user"), new User());
//    }
//
//    @Test
//    public void testSaveUser() {
//        when(bindingResult.hasErrors()).thenReturn(true);
//        String result = loginController.saveUser(user, bindingResult);
//        assertEquals(result, "createUser");
//        verifyZeroInteractions(userService);
//        when(bindingResult.hasErrors()).thenReturn(false);
//        result = loginController.saveUser(user, bindingResult);
//        assertEquals(result, "redirect:/login");
//        verify(userService).add(user);
//    }
//
//    @Test
//    public void testLogin() {
//        String result = loginController.login(mock(Model.class));
//        assertEquals(result, "login");
//    }
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
