package homefinance.money.currency;

import static homefinance.money.currency.CurrencyController.REDIRECT_URL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

  @Mock
  private CurrencyService currencyServiceMock;
  private CurrencyController controller;
  @Mock
  private Model model;
  @Mock
  private Currency currency;
  @Mock
  private BindingResult result;
  @Mock
  private CurrencyFacade currencyFacadeMock;

  @Before
  public void setUp() {
    this.controller = new CurrencyController(this.currencyServiceMock, this.currencyFacadeMock);
    PathSelector pathSelector = new PathSelectorTest();
    this.controller.setPathSelector(pathSelector);
  }

  @Test
  public void list_shouldReturnListTemplateAndAddCurrenciesAttributeWithFilledCurrencyDtoList() {
    //given
    List<CurrencyDto> currencyDtoList = Arrays.asList(mock(CurrencyDto.class), null);
    given(this.currencyFacadeMock.getAllCurrenciesDto())
        .willReturn(currencyDtoList);
    //when
    String actual = this.controller.list(this.model);
    //then
    assertThat(actual, is("currencies/list"));
    BDDAssertions.then(actual)
        .isEqualTo("currencies/list");
    BDDMockito.then(this.model)
        .should()
        .addAttribute("currencies", currencyDtoList);
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
    when(this.currencyServiceMock.getByID(id)).thenReturn(this.currency);
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
    BDDMockito.then(this.currencyServiceMock)
        .should()
        .fillDistinctCurrencies();
  }
}
