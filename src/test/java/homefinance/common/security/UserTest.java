package homefinance.common.security;

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
  public void testHasAdmin() {
    UserRole userRole = mock(UserRole.class);
    when(userRole.getRole()).thenReturn(Role.ADMIN);
    Set<UserRole> userRoles = new HashSet<>();
    userRoles.add(userRole);
    this.user.setUserRole(userRoles);
    assertTrue(this.user.hasAdmin());
    when(userRole.getRole()).thenReturn("test");
    assertFalse(this.user.hasAdmin());
  }
}
