package homefinance.money.currency;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyFacadeTest {

  @Mock
  private CurrencyService currencyServiceMock;
  private Currency currency1;
  private Currency currency2;
  private CurrencyFacade currencyFacade;

  @Before
  public void setUp() {
    this.currencyFacade = new CurrencyFacade(this.currencyServiceMock, new ModelMapper());
  }

  @Test
  public void getAllCurrencies_givenCurrencyServiceWillReturnTwoCurrencies_shouldReturnListWith2CurrencyDto() {
    //given
    this.initCurrency1And2();
    given(this.currencyServiceMock.getAllCurrencies())
        .willReturn(Arrays.asList(this.currency1, this.currency2));
    //when
    List<CurrencyDto> actualCurrenciesDto = this.currencyFacade.getAllCurrenciesDto();
    //then
    BDDMockito.then(this.currencyServiceMock)
        .should()
        .getAllCurrencies();
    BDDAssertions.then(actualCurrenciesDto.size())
        .isEqualTo(2);
    this.testCurrency(this.currency1, actualCurrenciesDto.get(0));
    this.testCurrency(this.currency2, actualCurrenciesDto.get(1));
  }

  private void initCurrency1And2() {
    this.currency1 = new Currency();
    this.currency1.setId(1);
    this.currency1.setCharCode("char1");
    this.currency1.setCode("code1");
    this.currency1.setName("name1");
    this.currency2 = new Currency();
    this.currency2.setId(1);
    this.currency2.setCharCode("char2");
    this.currency2.setCode("code2");
    this.currency2.setName("name2");
  }

  private void testCurrency(Currency currency1, CurrencyDto currencyDto1) {
    BDDAssertions.then(currencyDto1)
        .isEqualToComparingOnlyGivenFields(currency1, "id", "name", "code", "charCode");
  }

  @Test
  public void getAllCurrenciesDto_givenCurrencyServiceWillReturnNull_shouldReturnEmptyList() {
    given(this.currencyServiceMock.getAllCurrencies())
        .willReturn(null);
    List<CurrencyDto> actualList = this.currencyFacade.getAllCurrenciesDto();
    BDDAssertions.then(actualList.isEmpty())
        .isTrue();
  }
}