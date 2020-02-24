package homefinance.money.currency.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyService;
import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CurrencyServiceImplIntegrationTest {

  @Autowired
  CurrencyService currencyService;

  @Before
  public void setUp() {
      this.currencyService.list().forEach(currency -> this.currencyService.delete(currency.getId()));
  }

  @Test
  public void testAdd() {
    int size = this.currencyService.list().size();
    Currency currency = new Currency("USD", "840");
      this.currencyService.add(currency);
    assertTrue(this.currencyService.list().size() == size + 1);
    try {
        this.currencyService.add(new Currency("USD", "840"));
      fail("Exception must be thrown");
    } catch (Exception e) {
      if (!(e instanceof DuplicateFieldsException)) {
        fail("DuplicateFieldsException must be thrown");
      }
    }
  }

  @Test
  public void testUpdate() {
    Currency currency = new Currency("USD", "840");
      this.currencyService.add(currency);
    String newName = "updated";
    currency.setName(newName);
      this.currencyService.update(currency);
    Currency found = this.currencyService.getByID(currency.getId());
    assertEquals(newName, found.getName());
  }

  @Test
  public void testList() {
    int size = this.currencyService.list().size();
    List<Currency> list = new ArrayList<>(3);
    list.add(new Currency("test1", "1"));
    list.add(new Currency("test2", "2"));
    list.add(new Currency("test3", "3"));
    list.forEach(currency -> this.currencyService.add(currency));
    assertEquals(size + 3, this.currencyService.list().size());
  }

}
