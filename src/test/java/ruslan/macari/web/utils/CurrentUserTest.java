package ruslan.macari.web.utils;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ruslan.macari.domain.User;
import ruslan.macari.web.utils.impl.CurrentUserImpl;

public class CurrentUserTest {
    
    private final Map<String, User> users = new HashMap<>();
    private User user = mock(User.class);
    private final String id = "id1";
    private CurrentUser currentUser;
    
    public CurrentUserTest() {
    }
    
    @Before
    public void setUp() {
        currentUser = new CurrentUserImpl();
        currentUser.setUsers(users);
        user = mock(User.class);
    }
    
    @After
    public void cleanUp() {
        users.clear();
    }
    
    @Test
    public void testGet() {
        currentUser.add(id, user);
        User foundUser = currentUser.get(id);
        assertTrue(currentUser.get(id).equals(foundUser));
    }

    @Test
    public void testAdd() {
        currentUser.add(id, user);
        assertEquals(1, currentUser.size());
        assertTrue(currentUser.get(id).equals(user));
    }

    @Test
    public void testRemove() {
        currentUser.add(id, user);
        currentUser.remove(id);
        assertEquals(0, currentUser.size());
    }

    @Test
    public void testIsAdmin() {
        when(user.isAdmin()).thenReturn(true);
        currentUser.add(id, user);
        assertTrue(currentUser.isAdmin(id));
    }

    @Test
    public void testExists() {
        assertFalse(currentUser.exists(id));
        currentUser.add(id, user);
        assertTrue(currentUser.exists(id));
        
    }
    
}
