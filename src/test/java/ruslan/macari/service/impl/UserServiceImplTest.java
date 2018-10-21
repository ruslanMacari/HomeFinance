package ruslan.macari.service.impl;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ruslan.macari.config.AppConfig;
import ruslan.macari.config.WebConfig;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class UserServiceImplTest {
    
    @Autowired
    UserService userService;
    
    public UserServiceImplTest() {
        
    }

    @Test
    public void testAddUser() {
        int size = userService.listUsers().size();
        userService.addUser(new User());
        assertTrue(userService.listUsers().size() > size);
        
    }

//    @Test
//    public void testUpdateUser() {
//        System.out.println("updateUser");
//        User user = null;
//        UserServiceImpl instance = new UserServiceImpl();
//        instance.updateUser(user);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testListUsers() {
//        System.out.println("listUsers");
//        UserServiceImpl instance = new UserServiceImpl();
//        List<User> expResult = null;
//        List<User> result = instance.listUsers();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testListUsersLimit() {
//        System.out.println("listUsersLimit");
//        int limit = 0;
//        UserServiceImpl instance = new UserServiceImpl();
//        List<User> expResult = null;
//        List<User> result = instance.listUsersLimit(limit);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetUserById() {
//        System.out.println("getUserById");
//        int id = 0;
//        UserServiceImpl instance = new UserServiceImpl();
//        User expResult = null;
//        User result = instance.getUserById(id);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetUserByName() {
//        System.out.println("getUserByName");
//        String name = "";
//        UserServiceImpl instance = new UserServiceImpl();
//        User expResult = null;
//        User result = instance.getUserByName(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoveUser() {
//        System.out.println("removeUser");
//        int id = 0;
//        UserServiceImpl instance = new UserServiceImpl();
//        instance.removeUser(id);
//        fail("The test case is a prototype.");
//    }
    
}
