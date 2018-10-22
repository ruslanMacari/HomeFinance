package ruslan.macari.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    public CurrencyServiceImplTest() {
        
    }

    @Test
    public void testAdd() {
        int size = currencyService.list().size();
        currencyService.add(new Currency());
        assertTrue(currencyService.list().size() == size + 1);
    }
    
    @Test
    public void testUpdate() {
        Currency currency = new Currency();
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
        list.add(new Currency("test1"));
        list.add(new Currency("test2"));
        list.add(new Currency("test3"));
        for(Currency currency : list) {
            currencyService.add(currency);
        }
        assertEquals(size + 3, currencyService.list().size());
    }
    
}
