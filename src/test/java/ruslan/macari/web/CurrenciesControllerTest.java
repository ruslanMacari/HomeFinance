package ruslan.macari.web;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.springframework.ui.Model;
import ruslan.macari.service.CurrencyService;

public class CurrenciesControllerTest {
    
    private CurrencyService currencyService;
    private CurrenciesController controller;
    private Model model;
    
    public CurrenciesControllerTest() {
        currencyService = mock(CurrencyService.class);
        controller = new CurrenciesController();
        controller.setCurrencyService(currencyService);
        model = mock(Model.class);
    }
    
    @Test
    public void testList() {
        assertTrue(controller.list(model).equals("currencies/list"));
    }
    
    @Test
    public void testNewCurrency() {
        assertTrue(controller.newCurrency(model).equals("currencies/new"));
    }
    
}
