package ruslan.macari.web;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.security.User;
import ruslan.macari.service.UserService;
import ruslan.macari.web.exceptions.PageNotFoundException;

public class UsersControllerTest {

    private UsersController usersController;
    private Model model;
    private UserService userService;
    private PasswordEncoder encoder;
    private String rootname;
    private BindingResult result;
    private User user;
    private Validator validator;

    public UsersControllerTest() {
        userService = mock(UserService.class);
        encoder = mock(PasswordEncoder.class);
        rootname = "root";
        validator = mock(Validator.class);
        usersController = new UsersController();
        usersController.setUserService(userService);
        usersController.setEncoder(encoder);
        usersController.setRootname(rootname);
        usersController.setUpdateUserValidator(validator);
        usersController.setNewUserValidator(validator);
        model = mock(Model.class);
        result = mock(BindingResult.class);
        user = mock(User.class);
    }

    @Test
    public void testList() {
        System.out.println("list");
        assertTrue(usersController.list(model).equals("users/list"));
    }

    @Test
    public void testView() {
        System.out.println("view");
        Integer id = 100;
        when(user.getName()).thenReturn("test");
        when(userService.getById(id)).thenReturn(user);
        try {
            assertTrue(usersController.view(id, model).equals("users/view"));
        } catch (PageNotFoundException e) {
            fail("Exception must not be thrown!");
        }
        when(userService.getById(id)).thenReturn(null);
        try {
            usersController.view(id, model);
            fail("PageNotFoundException must be thrown!");
        } catch (PageNotFoundException e) {
        }
        when(userService.getById(id)).thenReturn(user);
        when(user.getName()).thenReturn(rootname);
        try {
            usersController.view(id, model);
            fail("PageNotFoundException must be thrown!");
        } catch (PageNotFoundException e) {
        }
    }

    @Test
    public void testPageNotFoundException() {
        System.out.println("pageNotFoundException");
        ModelAndView modelAndView = usersController.pageNotFoundException();
        assertTrue(modelAndView.getViewName().equals("resource-not-found"));
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        when(result.hasErrors()).thenReturn(true);
        Integer id = 100;
        String resultUpdate = usersController.update(user, result, id, true, true);
        assertTrue(resultUpdate.equals("users/view"));
        when(result.hasErrors()).thenReturn(false);
        when(userService.getById(id)).thenReturn(user);
        resultUpdate = usersController.update(user, result, id, true, true);
        assertTrue(resultUpdate.equals("redirect:/users"));
    }
    
    @Test
    public void testNewUser() {
        System.out.println("newUser");
        assertTrue(usersController.newUser(model).equals("users/new"));
    }
    
    @Test
    public void testSave() {
        System.out.println("save");
        when(result.hasErrors()).thenReturn(true);
        assertTrue(usersController.save(user, result, true).equals("users/new"));
        when(result.hasErrors()).thenReturn(false);
        assertTrue(usersController.save(user, result, true).equals("redirect:/users"));
    }
    
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        assertTrue(usersController.deleteUser(1).equals("redirect:/users"));
    }
    
    @Test
    public void testInitUserBinder() {
        System.out.println("deleteUser");
        WebDataBinder dataBinder = mock(WebDataBinder.class);
        usersController.initUserBinder(dataBinder);
        verify(dataBinder, never()).setValidator(validator);
        when(dataBinder.getTarget()).thenReturn(new Object());
        usersController.initUserBinder(dataBinder);
        verify(dataBinder, times(1)).setValidator(validator);
    }
    
    @Test
    public void testInitNewUserBinder() {
        System.out.println("deleteUser");
        WebDataBinder dataBinder = mock(WebDataBinder.class);
        usersController.initNewUserBinder(dataBinder);
        verify(dataBinder, never()).setValidator(validator);
        when(dataBinder.getTarget()).thenReturn(new Object());
        usersController.initNewUserBinder(dataBinder);
        verify(dataBinder, times(1)).setValidator(validator);
    }

}
