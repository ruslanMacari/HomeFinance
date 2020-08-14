package homefinance.user.entity;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class UserTest {

  private User user;

  public UserTest() {
    this.user = new User("test");
  }

  @Test
  public void testSetOneRole() {
    // test if no user role seted
    Set<UserRole> userRoleSet = this.user.getUserRole();
    String role = "role";
    this.user.setOneRole(role);
    assertEquals(1, userRoleSet.size());
    assertThat(userRoleSet.stream().findFirst().get().getRole(), is(role));
    // test if user role exist
    String newRole = "newRole";
    UserRole userRole = mock(UserRole.class);
    when(userRole.getRole()).thenReturn(newRole);
    userRoleSet.add(userRole);
    this.user.setOneRole(newRole);
    assertEquals(1, userRoleSet.size());
    assertThat(userRoleSet.stream().findFirst().get().getRole(), is(newRole));
  }

  @Test
  public void testIsAdmin() {
    UserRole userRole = mock(UserRole.class);
    when(userRole.getRole()).thenReturn(Role.ADMIN);
    Set<UserRole> userRoles = new HashSet<>();
    userRoles.add(userRole);
    this.user.setUserRole(userRoles);
    assertTrue(this.user.isAdmin());
    when(userRole.getRole()).thenReturn("test");
    assertFalse(this.user.isAdmin());
  }

  @Test
  public void isInRole_givenUserHasRole_returnTrue() {
    //given:
    user.addRole("role1");
    //when:
    boolean actual = user.isInRole("role1");
    //then:
    then(actual).isTrue();
  }

  @Test
  public void isInRole_givenUserDoesNotHaveRole_returnFalse() {
    //when:
    boolean actual = user.isInRole("role1");
    //then:
    then(actual).isFalse();
  }

  @Test
  public void addRole_givenRole_shouldAddRole() {
    //when:
    user.addRole("role1");
    user.addRole("role1");
    user.addRole("role2");
    //then:
    then(user.getUserRole().size()).isEqualTo(2);
    then(user.isInRole("role1")).isTrue();
    then(user.isInRole("role2")).isTrue();
  }
}
