package ruslan.macari.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ruslan.macari.domain.Currency;
import ruslan.macari.config.TestConfig;
import ruslan.macari.service.CurrencyService;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CurrencyServiceImplTest {
    
    @Autowired
    CurrencyService currencyService;
    
    @Before
    public void setUp() {
        currencyService.list().forEach(currency -> currencyService.delete(currency.getId()));
    }

    @Test
    public void testAdd() {
        int size = currencyService.list().size();
        Currency currency = new Currency("USD", "840");
        currencyService.add(currency);
        assertTrue(currencyService.list().size() == size + 1);
        try {
            currencyService.add(new Currency("USD", "840"));
            fail("Exception must be thrown");
        } catch (Exception e) {
            if (!(e instanceof DataIntegrityViolationException)) {
                fail("DataIntegrityViolationException must be thrown");
            }
        }
    }
    
    @Test
    public void testUpdate() {
        Currency currency = new Currency("USD", "840");
        currencyService.add(currency);
        String newName = "updated";
        currency.setName(newName);
        currencyService.update(currency);
        Currency found = currencyService.getByID(currency.getId());
        assertEquals(newName, found.getName());
    }

    @Test
    public void testList() {
        int size = currencyService.list().size();
        List<Currency> list = new ArrayList<>(3);
        list.add(new Currency("test1", "1"));
        list.add(new Currency("test2", "2"));
        list.add(new Currency("test3", "3"));
        list.forEach(currency -> currencyService.add(currency));
        assertEquals(size + 3, currencyService.list().size());
    }
    
}
