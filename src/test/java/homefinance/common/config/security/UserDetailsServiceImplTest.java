package homefinance.common.config.security;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.user.entity.User;
import homefinance.user.entity.UserRole;
import homefinance.user.service.UserService;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

  private UserDetailsServiceImpl userDetailsService;
  @Mock
  private UserService userService;

  @Before
  public void setUp() {
    userDetailsService = new UserDetailsServiceImpl(userService);
  }

  @Test
  public void loadUserByUsername() {
    // given:
    User user = mockUser("test", "role user");
    given(userService.getByName("test")).willReturn(user);
    // when:
    UserDetails actual = userDetailsService.loadUserByUsername("test");
    // then:
    then(actual.getAuthorities().size()).isEqualTo(1);
    then(((GrantedAuthority)actual.getAuthorities().toArray()[0]).getAuthority()).isEqualTo("role user");
  }

  @SuppressWarnings("SameParameterValue")
  private User mockUser(String name, String role) {
    User user = mock(User.class);
    given(user.getName()).willReturn(name);
    given(user.getPassword()).willReturn("pass");
    given(user.isEnabled()).willReturn(true);
    Set<UserRole> set = mockUserRoles(role);
    given(user.getUserRole()).willReturn(set);
    return user;
  }

  private Set<UserRole> mockUserRoles(String role) {
    Set<UserRole> set = new HashSet<>();
    UserRole userRole = mock(UserRole.class);
    given(userRole.getRole()).willReturn(role);
    set.add(userRole);
    return set;
  }


}
