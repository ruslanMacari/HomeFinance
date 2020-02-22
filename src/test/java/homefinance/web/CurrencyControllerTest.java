package homefinance.web;

import homefinance.money.currency.CurrencyController;
import homefinance.util.impl.PathSelectorTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyService;
import homefinance.util.PathSelector;

public class CurrencyControllerTest {
    
    private CurrencyService currencyService;
    private CurrencyController controller;
    private Model model;
    private Currency currency;
    private PathSelector pathSelector;
    private BindingResult result;
    
    @Before
    public void initialize() {
        currencyService = mock(CurrencyService.class);
        controller = new CurrencyController();
        controller.setCurrencyService(currencyService);
        model = mock(Model.class);
        currency = mock(Currency.class);
        pathSelector = new PathSelectorTest();
        controller.setPathSelector(pathSelector);
        result = mock(BindingResult.class);
    }
    
    @Test
    public void testList() {
        assertTrue(controller.list(model).equals(CurrencyController.LIST_PATH));
    }
    
    @Test
    public void testNewCurrency() {
        assertTrue(controller.newCurrency(model).equals(CurrencyController.NEW_PATH));
    }
    
    @Test
    public void testSave() {
        when(result.hasErrors()).thenReturn(true);
        assertEquals(controller.save(currency, result), CurrencyController.NEW_PATH);
        when(result.hasErrors()).thenReturn(false);
        assertEquals(controller.save(currency, result), CurrencyController.REDIRECT_PATH);
    }
    
    @Test
    public void testView() {
        Integer id = 5;
        when(currencyService.getByID(id)).thenReturn(currency);
        assertEquals(controller.view(id, model), CurrencyController.VIEW_PATH);
    }
    
    @Test
    public void testUpdate() {
        when(result.hasErrors()).thenReturn(true);
        String path = controller.update(currency, result);
        assertEquals(path, CurrencyController.VIEW_PATH);
        when(result.hasErrors()).thenReturn(false);
        path = controller.update(currency, result);
        assertEquals(path, CurrencyController.REDIRECT_PATH);
    }
    
    @Test
    public void testDeleteUser() {
        assertEquals(controller.deleteUser(1), CurrencyController.REDIRECT_PATH);
    }
    
}
