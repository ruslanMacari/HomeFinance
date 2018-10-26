package ruslan.macari.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
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
    UserService userService;
    
    public UserServiceImplTest() {
        
    }

    @Test
    public void testAdd() {
        int size = userService.list().size();
        userService.add(new User());
        assertTrue(userService.list().size() == size + 1);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setName("user");
        userService.add(user);
        String newName = "user new";
        user.setName(newName);
        userService.update(user);
        String dbUserName = userService.getById(user.getId()).getName();
        assertTrue(dbUserName.equals(newName));
    }

    @Test
    public void testList() {
        int size = userService.list().size();
        addThreeUsers();
        assertEquals(size + 3, userService.list().size());
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
        User user = new User("test");
        userService.add(user);
        assertEquals(user.getName(), userService.getById(user.getId()).getName());
    }

    @Test
    public void testGetByName() {
        User user = new User("test get by name");
        userService.add(user);
        assertEquals(user, userService.getByName(user.getName()));
    }

    @Test
    public void testDelete() {
        User user = new User("Test delete");
        userService.add(user);
        int size = userService.list().size();
        userService.delete(user.getId());
        assertEquals(size - 1, userService.list().size());
    }
    
    @Test
    public void testGetAdmin() {
        assertNull(userService.getAdmin());
        User user = new User("test");
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
        for (User user : userService.list()) {
            userService.delete(user.getId());
        }
        User admin = new User("admin testGetSimpleUsers");
        admin.setAdmin(true);
        userService.add(admin);
        User simpleUser = new User ("user testGetSimpleUsers");
        userService.add(simpleUser);
        List<User> simpleUsers = userService.getSimpleUsers();
        assertEquals(1, simpleUsers.size());
        for(User user : simpleUsers) {
            if (user.equals(admin)) {
                fail("admin user must not be found");
                break;
            }
        }
    }

}
