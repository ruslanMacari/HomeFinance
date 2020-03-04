package homefinance.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.junit.Test;

public class RoleTest {

  @Test
  public void testGetRoles() {
    Set<String> roles = Role.getRoles();
    assertEquals(2, roles.size());
    assertTrue(roles.contains(Role.ADMIN));
    assertTrue(roles.contains(Role.USER));
  }

}
