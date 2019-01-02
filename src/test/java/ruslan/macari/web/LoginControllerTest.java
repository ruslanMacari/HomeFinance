package ruslan.macari.web;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.validation.BindingResult;
import ruslan.macari.security.User;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.springframework.ui.Model;
import ruslan.macari.service.UserService;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LoginControllerTest {
    
    private LoginController loginController;
    private UserService userService;
    private User user;
    private BindingResult bindingResult;
    private Model model;
    private String rootname = "root";
    private String rootpassword = "pass";
    private PasswordEncoder encoder;
    //private Validator validator;
    
    @Before
    public void setUp() {
        model = mock(Model.class);
        loginController = new LoginController();
        userService = mock(UserService.class);
        loginController.setUserService(userService);
        user = mock(User.class);
        bindingResult = mock(BindingResult.class);
        loginController.setRootname(rootname);
        loginController.setRootpassword(rootpassword);
        encoder = mock(PasswordEncoder.class);
        loginController.setEncoder(encoder);
        //validator = mock(Validator.class);
        //loginController.setNewUserValidator(validator);
    }
    
    @After
    public void cleanUp() {
    }
    
    @Test
    public void testInit() {
        when(userService.getByName(rootname)).thenReturn(user);
        loginController.init();
        verify(userService, times(0)).add(user);
        when(userService.getByName(rootname)).thenReturn(null);
        when(encoder.encode(rootpassword)).thenReturn(rootpassword);
        loginController.init();
        verify(encoder, times(1)).encode(rootpassword);
    }
    
    @Test
    public void testAuthorization() {
        SecurityContext contextBefore = SecurityContextHolder.getContext();
        Authentication auth = getMockAuth();
        when(auth.isAuthenticated()).thenReturn(true);
        assertTrue(loginController.login(model).equals("redirect:/"));
        when(auth.isAuthenticated()).thenReturn(false);
        assertTrue(loginController.login(model).equals("/auth/login"));
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
        assertTrue(loginController.registration(model).equals("/auth/registration"));
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
        assertTrue(result.equals("redirect:/login/registration"));
        when(bindingResult.hasErrors()).thenReturn(false);
        result = loginController.registration(user, bindingResult, attributes, model);
        assertTrue(result.equals("redirect:/login"));
    }
    
//    @Test
//    public void testInitUserBinder() {
//        WebDataBinder dataBinder = mock(WebDataBinder.class);
//        loginController.initUserBinder(dataBinder);
//        verify(dataBinder, never()).setValidator(validator);
//        when(dataBinder.getTarget()).thenReturn(new Object());
//        loginController.initUserBinder(dataBinder);
//        verify(dataBinder, times(1)).setValidator(validator);
//    }
    
}
