package ruslan.macari.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ruslan.macari.service.UserService;

public class UserDetailsServiceImplTest {
    
    private String username = "test";
    
    public UserDetailsServiceImplTest() {
    }

    @Test
    public void testLoadUserByUsername() {
        System.out.println("loadUserByUsername");
        UserDetailsServiceImpl serviceImpl = new UserDetailsServiceImpl();
        serviceImpl.setUserService(getUserService());
        UserDetails details = serviceImpl.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
        assertTrue(authorities.size() == 1);
        for (GrantedAuthority auth : authorities) {
            assertEquals(Role.USER, auth.getAuthority());
        }
    }
    
    private UserService getUserService() {
        UserService userService = mock(UserService.class);
        User user = getUser();
        when(userService.getByName(username)).thenReturn(user);
        return userService;
    }
    
    private User getUser() {
        User user = mock(User.class);
        when(user.getName()).thenReturn(username);
        when(user.getPassword()).thenReturn("pass");
        when(user.isEnabled()).thenReturn(true);
        Set<UserRole> set = getUserRoles();
        when(user.getUserRole()).thenReturn(set);
        return user;
    }

    private Set<UserRole> getUserRoles() {
        Set<UserRole> set = new HashSet<>();
        UserRole userRole = mock(UserRole.class);
        when(userRole.getRole()).thenReturn(Role.USER);
        set.add(userRole);
        return set;
    }

    
    
}
