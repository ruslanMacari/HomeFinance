package ruslan.macari.web.utils;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ruslan.macari.domain.User;

public class CurrentUserTest {
    
    private final Map<String, User> users = new HashMap<>();
    private final User user = mock(User.class);
    private final String id = "id1";
    
    public CurrentUserTest() {
        init();
    }
    
    private void init() {
        CurrentUser.setUsers(users);
    }
    
    @Before
    public void before() {
        users.clear();
    }
    
    @Test
    public void testGet() {
        CurrentUser.add(id, user);
        User foundUser = CurrentUser.get(id);
        assertTrue(CurrentUser.get(id).equals(foundUser));
    }

    @Test
    public void testAdd() {
        CurrentUser.add(id, user);
        assertEquals(1, CurrentUser.size());
        assertTrue(CurrentUser.get(id).equals(user));
    }

    @Test
    public void testRemove() {
        CurrentUser.add(id, user);
        CurrentUser.remove(id);
        assertEquals(0, CurrentUser.size());
    }

    @Test
    public void testIsAdmin() {
        User user = mock(User.class);
        when(user.isAdmin()).thenReturn(true);
        CurrentUser.add(id, user);
        assertTrue(CurrentUser.isAdmin(id));
    }

    @Test
    public void testExists() {
        assertFalse(CurrentUser.exists(id));
        CurrentUser.add(id, user);
        assertTrue(CurrentUser.exists(id));
        
    }
    
}
