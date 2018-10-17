/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruslan.macari.controllers;

import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.domain.User;
import ruslan.macari.domain.UserLogin;

/**
 *
 * @author User
 */
public class LoginControllerIT {
    
    public LoginControllerIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testMain() {
        System.out.println("main");
        MockHttpSession session = new MockHttpSession(); 
        //session.setAttribute("user", new User());
        LoginController instance = new LoginController();
        String expResult = "redirect:/home";
        String result = instance.main(session);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

//    @Test
//    public void testCreateUser() {
//        System.out.println("createUser");
//        HttpSession session = null;
//        Model model = null;
//        LoginController instance = new LoginController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.createUser(session, model);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
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
