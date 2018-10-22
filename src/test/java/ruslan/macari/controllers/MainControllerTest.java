package ruslan.macari.controllers;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.CurrencyService;

public class MainControllerTest {
    
    private MainController mainController;
    private CurrencyService currencyService;
    
    public MainControllerTest() {
        init();
    }
    
    private void init() {
        mainController = new MainController();
        currencyService = mock(CurrencyService.class);
        mainController.setCurrencyService(currencyService);
    }
    
    @Test
    public void testHome() {
        assertEquals("home", mainController.home());
    }

    @Test
    public void testCreateCurrency() {
        Model model = mock(Model.class);
        String result = mainController.createCurrency(model);
        assertEquals("createCurrency", result);
        verify(currencyService).list();
        verify(model).addAttribute("currency", new Currency());
        verify(model).addAttribute("listCurrency", currencyService.list());
    }

    @Test
    public void testSaveCurrency() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        Currency currency = mock(Currency.class);
        String result = mainController.saveCurrency(currency, bindingResult);
        assertEquals(result, "createCurrency");
        verifyZeroInteractions(currencyService);
        when(bindingResult.hasErrors()).thenReturn(false);
        result = mainController.saveCurrency(currency, bindingResult);
        assertEquals(result, "redirect:/home");
        verify(currencyService).add(currency);
    }
    
}
