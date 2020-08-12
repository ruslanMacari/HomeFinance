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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

  private static final String USER_NAME = "user test";
  private static final String USER_ROLE = "role user";
  private static final String PASSWORD = "password";
  private UserDetailsServiceImpl userDetailsService;
  @Mock
  private UserService userService;

  @Before
  public void setUp() {
    userDetailsService = new UserDetailsServiceImpl(userService);
  }

  @Test
  public void loadUserByUsername_givenUserWithNameAndRole_returnUserDetails() {
    // given:
    User user = mockUser(USER_NAME, USER_ROLE, PASSWORD, true);
    given(userService.getByName(USER_NAME)).willReturn(user);
    // when:
    UserDetails actual = userDetailsService.loadUserByUsername(USER_NAME);
    // then:
    then(actual.getUsername()).isEqualTo(USER_NAME);
    then(actual.getPassword()).isEqualTo(PASSWORD);
    then(actual.isEnabled()).isTrue();
    then(actual.getAuthorities().size()).isEqualTo(1);
    then(((GrantedAuthority)actual.getAuthorities().toArray()[0]).getAuthority()).isEqualTo(USER_ROLE);
  }

  @SuppressWarnings("SameParameterValue")
  private User mockUser(String name, String role, String password, boolean enabled) {
    User user = mock(User.class);
    given(user.getName()).willReturn(name);
    given(user.getPassword()).willReturn(password);
    given(user.isEnabled()).willReturn(enabled);
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

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_givenUserIsNull_throwUsernameNotFoundException() {
    //given:
    given(userService.getByName(USER_NAME)).willReturn(null);
    //when:
    userDetailsService.loadUserByUsername(USER_NAME);
  }
}
