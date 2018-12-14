package ruslan.macari.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class CurrencyTest {
    
    private final Currency currency = new Currency();
    
    public CurrencyTest() {
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
