package ruslan.macari.domain;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CurrentUserTest {
    
    private User user = mock(User.class);

    public CurrentUserTest() {
    }

//    @Test
//    public void testRemove() {
//        CurrentUser.setUser(user);
//        assertTrue(CurrentUser.exists());
//        CurrentUser.remove();
//        assertFalse(CurrentUser.exists());
//    }
//
//    @Test
//    public void testIsAdmin() {
//        when(user.isAdmin()).thenReturn(true);
//        CurrentUser.setUser(user);
//        assertTrue(CurrentUser.isAdmin());
//    }
//
//    @Test
//    public void testExists() {
//        CurrentUser.remove();
//        assertFalse(CurrentUser.exists());
//    }
    
}
