package homefinance.user.entity;

import static homefinance.user.entity.Role.ADMIN;
import static homefinance.user.entity.Role.USER;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UserTest {

  private final User user;

  public UserTest() {
    user = new User("test");
  }

  @Test
  public void testSetOneRole() {
    // test if no user role seted
    Set<UserRole> userRoleSet = user.getUserRole();
    user.setOneRole(ADMIN);
    assertEquals(1, userRoleSet.size());
    assertThat(userRoleSet.stream().findFirst().get().getRole(), is(ADMIN));
    // test if user role exist
    UserRole userRole = mock(UserRole.class);
    when(userRole.getRole()).thenReturn(USER);
    userRoleSet.add(userRole);
    user.setOneRole(USER);
    assertEquals(1, userRoleSet.size());
    assertThat(userRoleSet.stream().findFirst().get().getRole(), is(USER));
  }

  @Test
  public void testIsAdmin() {
    UserRole userRole = mock(UserRole.class);
    when(userRole.getRole()).thenReturn(ADMIN);
    Set<UserRole> userRoles = new HashSet<>();
    userRoles.add(userRole);
    user.setUserRole(userRoles);
    assertTrue(user.isAdmin());
    when(userRole.getRole()).thenReturn(Role.USER);
    assertFalse(user.isAdmin());
  }

  @Test
  public void isInRole_givenUserHasRole_returnTrue() {
    //given:
    user.addRole(ADMIN);
    //when:
    boolean actual = user.isInRole(ADMIN);
    //then:
    then(actual).isTrue();
  }

  @Test
  public void isInRole_givenUserDoesNotHaveRole_returnFalse() {
    //when:
    boolean actual = user.isInRole(Role.USER);
    //then:
    then(actual).isFalse();
  }

  @Test
  public void addRole_givenRole_shouldAddRole() {
    //when:
    user.addRole(ADMIN);
    user.addRole(ADMIN);
    user.addRole(Role.USER);
    //then:
    then(user.getUserRole().size()).isEqualTo(2);
    then(user.isInRole(Role.USER)).isTrue();
    then(user.isInRole(ADMIN)).isTrue();
  }
}
