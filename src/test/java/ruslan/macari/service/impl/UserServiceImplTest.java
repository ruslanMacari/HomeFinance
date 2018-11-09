package ruslan.macari.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ruslan.macari.domain.User;
import ruslan.macari.config.TestConfig;
import ruslan.macari.service.UserService;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {
    
    @Autowired
    private UserService userService;
    private User user;
    
    public UserServiceImplTest() {
        user = new User ("test");
    }
    
    @Before
    public void before() {
        for (User u : userService.list()) {
            userService.delete(u.getId());
        }
        user.setAdmin(false);
    }

    @Test
    public void testAdd() {
        userService.add(user);
        assertTrue(userService.list().size() == 1);
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
        list.add(new User("test1"));
        list.add(new User("test2"));
        list.add(new User("test3"));
        for(User user : list) {
            userService.add(user);
        }
    }

    @Test
    public void testListLimit() {
        addThreeUsers();
        assertEquals(2, userService.listLimit(2).size());
    }

    @Test
    public void testGetById() {
        userService.add(user);
        assertEquals(user.getName(), userService.getById(user.getId()).getName());
    }

    @Test
    public void testGetByName() {
        userService.add(user);
        assertEquals(user, userService.getByName(user.getName()));
    }

    @Test
    public void testDelete() {
        userService.add(user);
        userService.delete(user.getId());
        assertEquals(0, userService.list().size());
    }
    
    @Test
    public void testGetAdmin() {
        assertNull(userService.getAdmin());
        user.setAdmin(true);
        userService.add(user);
        assertNotNull(userService.getAdmin());
    }
    
    @Test
    public void testGetByNameAndPassword() {
        String name = "test GetByNameAndPassword";
        String password = "password";
        User user = new User(name, password);
        userService.add(user);
        assertNotNull(userService.getByNameAndPassword(name, password));
    }
    
    @Test
    public void testGetSimpleUsers() {
        User admin = new User("admin");
        admin.setAdmin(true);
        userService.add(admin);
        userService.add(user);
        List<User> simpleUsers = userService.getSimpleUsers();
        assertEquals(1, simpleUsers.size());
        for(User u : simpleUsers) {
            if (u.equals(admin)) {
                fail("admin user must not be found");
                break;
            }
        }
    }
    
    @Test
    public void testGetByNameExceptID() {
        String name = "same user name";
        User user1 = new User(name);
        userService.add(user1);
        User user2 = new User(name);
        userService.add(user2);
        User foundUser = userService.getByNameExceptID(name, user1.getId());
        assertEquals(user2, foundUser);
    }
    
    @Test
    public void testUsersExceptRoot() {
        User root = new User("root");
        userService.add(root);
        List<User> result = userService.usersExceptRoot();
        assertEquals(result.size(), 0);
    }

}
