package ruslan.macari.security;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoleTest {
    
    public RoleTest() {
    }
    
    @Test
    public void testGetRoles() {
        Set<String> roles = Role.getRoles();
        assertEquals(2, roles.size());
    }
    
}
