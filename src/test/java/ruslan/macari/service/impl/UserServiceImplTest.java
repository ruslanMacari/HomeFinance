package ruslan.macari.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ruslan.macari.security.User;
import ruslan.macari.config.TestConfig;
import ruslan.macari.security.Role;
import ruslan.macari.service.UserService;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {
    
    @Autowired
    private UserService userService;
    private User user;
    
    @Value("${db.username}")
    private String rootname;
    
    public UserServiceImplTest() {
        user = new User ("test", "pass");
        user.setOneRole(Role.USER);
    }
    
    @Before
    public void before() {
        userService.list().forEach(u -> userService.delete(u.getId()));
    }
    
    @Test
    public void testAdd() {
        userService.add(user);
        List<User> list = userService.list();
        assertTrue(list.size() == 1);
        assertEquals(user, list.get(0));
        
    }

    @Test
    public void testUpdate() {
        userService.add(user);
        String newName = "user new";
        user.setName(newName);
        userService.update(user);
        String dbUserName = userService.getById(user.getId()).getName();
        assertTrue(dbUserName.equals(newName));
    }

    @Test
    public void testList() {
        addThreeUsers();
        assertEquals(3, userService.list().size());
    }
    
    private void addThreeUsers() {
        List<User> list = new ArrayList<>(3);
        list.add(new User("test1", "pass"));
        list.add(new User("test2", "pass"));
        list.add(new User("test3", "pass"));
        list.forEach(user -> userService.add(user));
    }

    @Test
    public void testListLimit() {
        addThreeUsers();
        assertTrue(userService.listLimit(2).size() == 2);
    }

    @Test
    public void testGetById() {
        userService.add(user);
        User foundUser = userService.getById(user.getId());
        assertTrue(foundUser.equals(user));
    }

    @Test
    public void testGetByName() {
        userService.add(user);
        User foundUser = userService.getByName(user.getName());
        assertTrue(foundUser.equals(user));
    }

    @Test
    public void testDelete() {
        userService.add(user);
        userService.delete(user.getId());
        assertTrue(userService.list().isEmpty());
    }
    
    @Test
    public void testGetByNameAndPassword() {
        String name = "test GetByNameAndPassword";
        String password = "password";
        User userNamePass = new User(name, password);
        userService.add(userNamePass);
        User foundUser = userService.getByNameAndPassword(name, password);
        assertTrue(foundUser.equals(userNamePass));
    }
    
    @Test
    public void testGetSimpleUsers() {
        User admin = new User("admin", "pass");
        admin.setOneRole(Role.ADMIN);
        userService.add(admin);
        userService.add(user);
        List<User> simpleUsers = userService.getSimpleUsers();
        assertTrue(simpleUsers.size() == 1);
        for(User u : simpleUsers) {
            if (u.equals(admin)) {
                fail("admin user must not be found");
                break;
            }
        }
    }
    
    
    @Test
    public void testUsersExceptRoot() {
        User root = new User(rootname, "pass");
        userService.add(root);
        List<User> result = userService.usersExceptRoot();
        assertTrue(result.isEmpty());
    }

}
