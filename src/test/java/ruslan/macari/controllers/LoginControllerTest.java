package ruslan.macari.controllers;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.domain.User;

public class LoginControllerTest {
    
    private MockHttpSession session;
    private LoginController loginController;
    
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
//
//    @Test
//    public void testSaveUser() {
//        System.out.println("saveUser");
//        User user = null;
//        BindingResult result_2 = null;
//        Model model = null;
//        LoginController instance = new LoginController();
//        String expResult = "";
//        String result = instance.saveUser(user, result_2, model);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
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
