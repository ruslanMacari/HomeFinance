package ruslan.macari.controllers;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.domain.User;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verify;
import ruslan.macari.service.UserService;

public class LoginControllerTest {
    
    private final MockHttpSession session;
    private final LoginController loginController;
    
    public LoginControllerTest() {
        session = new MockHttpSession();
        loginController = new LoginController();
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
        User user = new User();
        UserService userService = mock(UserService.class);
        loginController.setUserService(userService);
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = loginController.saveUser(user, bindingResult);
        assertEquals(result, "createUser");
        verifyZeroInteractions(userService);
        when(bindingResult.hasErrors()).thenReturn(false);
        result = loginController.saveUser(user, bindingResult);
        assertEquals(result, "redirect:/login");
        verify(userService, Mockito.times(1));
    }
//
//    @Test
//    public void testLogin() {
//        System.out.println("login");
//        Model model = null;
//        LoginController instance = new LoginController();
//        String expResult = "";
//        String result = instance.login(model);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCheckUser() {
//        System.out.println("checkUser");
//        UserLogin user = null;
//        BindingResult result_2 = null;
//        Model model = null;
//        HttpSession session = null;
//        LoginController instance = new LoginController();
//        String expResult = "";
//        String result = instance.checkUser(user, result_2, model, session);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
    
}
