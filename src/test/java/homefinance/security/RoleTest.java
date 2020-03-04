package homefinance.security;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import org.junit.Test;

public class RoleTest {

  public RoleTest() {
  }

  @Test
  public void testGetRoles() {
    Set<String> roles = Role.getRoles();
    assertEquals(2, roles.size());
  }

}
