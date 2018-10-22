package ruslan.macari.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class CurrencyTest {
    
    private final Currency currency = new Currency();
    
    public CurrencyTest() {
    }

    @Test
    public void testHashCode() {
        currency.setId(10);
        int result = currency.hashCode();
        assertEquals(result, 29 * 7 + 10);
    }

    @Test
    public void testEquals() {
        currency.setId(10);
        assertTrue(currency.equals(new Currency(10)));
    }

    @Test
    public void testToString() {
        int id = 10;
        String name = "test currency";
        currency.setId(id);
        currency.setName(name);
        String expected = "Currency{" + "id=" + id + ", name=" + name + '}';
        assertEquals(expected, currency.toString());
    }
    
}
