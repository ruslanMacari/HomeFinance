package ruslan.macari.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    private final User user = new User();
    
    public UserTest() {
    }

    @Test
    public void testHashCode() {
        user.setId(10);
        int result = user.hashCode();
        assertEquals(result, 97 * 7 + 10);
    }

    @Test
    public void testEquals() {
        user.setId(10);
        assertTrue(user.equals(new User(10)));
    }
    
    @Test
    public void testToString() {
        int id = 10;
        String name = "test user";
        String pass = "test pass";
        user.setId(id);
        user.setName(name);
        user.setPassword(pass);
        String expected = "User{" + "id=" + id + ", name=" + name + ", password=" + pass + '}';
        assertEquals(expected, user.toString());
    }
    
}
