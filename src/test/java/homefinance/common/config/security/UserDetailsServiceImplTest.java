package homefinance.common.config.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import homefinance.user.entity.UserRole;
import homefinance.user.service.UserService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsServiceImplTest {

  private String username = "test";

  @Test
  public void testLoadUserByUsername() {
    UserDetailsServiceImpl serviceImpl = new UserDetailsServiceImpl();
    serviceImpl.setUserService(this.getUserService());
    UserDetails details = serviceImpl.loadUserByUsername(this.username);
    Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
    assertEquals(1, authorities.size());
    assertThat(authorities.stream().findFirst().get().getAuthority(), is(Role.USER));
  }

  private UserService getUserService() {
    UserService userService = mock(UserService.class);
    User user = this.getUser();
    when(userService.getByName(this.username)).thenReturn(user);
    return userService;
  }

  private User getUser() {
    User user = mock(User.class);
    when(user.getName()).thenReturn(this.username);
    when(user.getPassword()).thenReturn("pass");
    when(user.isEnabled()).thenReturn(true);
    Set<UserRole> set = this.getUserRoles();
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
