package ruslan.macari.security;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {
    
    public UserTest() {
    }

    @Test
    public void testSetOneRole() {
        System.out.println("setOneRole");
        // test if no user role seted
        User user = new User ("test");
        Set<UserRole> userRoleSet = user.getUserRole();
        String role = "role";
        user.setOneRole(role);
        assertEquals(1, userRoleSet.size());
        userRoleSet.forEach((item) -> {
            assertTrue(item.getRole().equals(role));
        });
        // test if user role exist
        String newRole = "newRole";
        user.setOneRole(newRole);
        assertTrue(userRoleSet.size() == 1);
        userRoleSet.forEach((item) -> {
            assertTrue(item.getRole().equals(newRole));
        });
        
    }

    @Test
    public void testHasAdmin() {
        UserRole userRole = mock(UserRole.class);
        when(userRole.getRole()).thenReturn(Role.ADMIN);
        User user = new User ("test");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setUserRole(userRoles);
        assertTrue(user.hasAdmin());
        when(userRole.getRole()).thenReturn("test");
        assertFalse(user.hasAdmin());
    }
}
