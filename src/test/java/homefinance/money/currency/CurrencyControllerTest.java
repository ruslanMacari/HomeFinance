package homefinance.money.currency;

import static homefinance.money.currency.CurrencyController.REDIRECT_URL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.money.currency.entity.Currency;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class CurrencyControllerTest {

  private CurrencyService currencyService;
  private CurrencyController controller;
  private Model model;
  private Currency currency;
  private BindingResult result;

  @Before
  public void initialize() {
    this.currencyService = mock(CurrencyService.class);
    this.controller = new CurrencyController(this.currencyService);
    this.model = mock(Model.class);
    this.currency = mock(Currency.class);
    PathSelector pathSelector = new PathSelectorTest();
    this.controller.setPathSelector(pathSelector);
    this.result = mock(BindingResult.class);
  }

  @Test
  public void testList() {
    assertThat(this.controller.list(this.model), is("currencies/list"));
  }

  @Test
  public void testNewCurrency() {
    assertThat(this.controller.newCurrency(this.model), is("currencies/new"));
  }

  @Test
  public void testSave() {
    when(this.result.hasErrors()).thenReturn(true);
    assertThat(this.controller.save(this.currency, this.result), is("currencies/new"));
    when(this.result.hasErrors()).thenReturn(false);
    assertThat(this.controller.save(this.currency, this.result),
        is(REDIRECT_URL));
  }

  @Test
  public void testView() {
    Integer id = 5;
    when(this.currencyService.getByID(id)).thenReturn(this.currency);
    assertThat(this.controller.view(id, this.model), is("currencies/view"));
  }

  @Test
  public void testUpdate() {
    when(this.result.hasErrors()).thenReturn(true);
    String path = this.controller.update(this.currency, this.result);
    assertThat(path, is("currencies/view"));
    when(this.result.hasErrors()).thenReturn(false);
    path = this.controller.update(this.currency, this.result);
    assertThat(path, is(REDIRECT_URL));
  }

  @Test
  public void testDeleteUser() {
    assertThat(this.controller.deleteUser(1), is(REDIRECT_URL));
  }

  @Test
  public void fillCurrencies_shouldReturnRedirectUrl() {
    String actual = this.controller.fillCurrencies();
    BDDAssertions.then(actual)
        .isEqualTo(REDIRECT_URL);
    BDDMockito.then(this.currencyService)
        .should()
        .fillDistinctCurrencies();
  }
}
