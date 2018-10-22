package ruslan.macari.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ruslan.macari.config.AppConfig;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
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

}
