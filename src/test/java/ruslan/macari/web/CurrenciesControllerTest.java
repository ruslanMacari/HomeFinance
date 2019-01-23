package ruslan.macari.web;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.CurrencyService;
import ruslan.macari.util.PathSelector;
import ruslan.macari.util.impl.PathSelectorTest;

public class CurrenciesControllerTest {
    
    private CurrencyService currencyService;
    private CurrenciesController controller;
    private Model model;
    private Currency currency;
    private PathSelector pathSelector;
    private BindingResult result;
    
    @Before
    public void initialize() {
        currencyService = mock(CurrencyService.class);
        controller = new CurrenciesController();
        controller.setCurrencyService(currencyService);
        model = mock(Model.class);
        currency = mock(Currency.class);
        pathSelector = new PathSelectorTest();
        controller.setPathSelector(pathSelector);
        result = mock(BindingResult.class);
    }
    
    @Test
    public void testList() {
        assertTrue(controller.list(model).equals(CurrenciesController.LIST_PATH));
    }
    
    @Test
    public void testNewCurrency() {
        assertTrue(controller.newCurrency(model).equals(CurrenciesController.NEW_PATH));
    }
    
    @Test
    public void testSave() {
        when(result.hasErrors()).thenReturn(true);
        assertEquals(controller.save(currency, result), CurrenciesController.NEW_PATH);
        when(result.hasErrors()).thenReturn(false);
        assertEquals(controller.save(currency, result), CurrenciesController.REDIRECT_PATH);
    }
    
    @Test
    public void testView() {
        Integer id = 5;
        when(currencyService.getByID(id)).thenReturn(currency);
        assertEquals(controller.view(id, model), CurrenciesController.VIEW_PATH);
    }
    
    @Test
    public void testUpdate() {
        when(result.hasErrors()).thenReturn(true);
        String path = controller.update(currency, result, 1);
        assertEquals(path, CurrenciesController.VIEW_PATH);
        when(result.hasErrors()).thenReturn(false);
        path = controller.update(currency, result, 1);
        assertEquals(path, CurrenciesController.REDIRECT_PATH);
    }
    
    @Test
    public void testDeleteUser() {
        assertEquals(controller.deleteUser(1), CurrenciesController.REDIRECT_PATH);
    }
    
}
