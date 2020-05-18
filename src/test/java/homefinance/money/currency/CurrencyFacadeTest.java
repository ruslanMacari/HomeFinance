package homefinance.money.currency;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;

import homefinance.common.exception.PageNotFoundException;
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
  private ModelMapper modelMapper;

  @Before
  public void setUp() {
    this.modelMapper = new ModelMapper();
    this.currencyFacade = new CurrencyFacade(this.currencyServiceMock, this.modelMapper);
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
    BDDMockito.then(this.currencyServiceMock).should().getAllCurrencies();
    BDDAssertions.then(actualCurrenciesDto.size()).isEqualTo(2);
    this.testCurrency(this.currency1, actualCurrenciesDto.get(0));
    this.testCurrency(this.currency2, actualCurrenciesDto.get(1));
  }

  private void initCurrency1And2() {
    this.initCurrency1();
    this.currency2 = new Currency();
    this.currency2.setId(1);
    this.currency2.setCharCode("char2");
    this.currency2.setCode("code2");
    this.currency2.setName("name2");
  }

  private void initCurrency1() {
    this.currency1 = new Currency();
    this.currency1.setId(1);
    this.currency1.setCharCode("char1");
    this.currency1.setCode("code1");
    this.currency1.setName("name1");
  }

  private void testCurrency(Currency currency1, CurrencyDto currencyDto1) {
    BDDAssertions.then(currencyDto1)
        .isEqualToComparingOnlyGivenFields(currency1, "id", "name", "code", "charCode");
  }

  @Test
  public void getAllCurrenciesDto_givenCurrencyServiceWillReturnNull_shouldReturnEmptyList() {
    given(this.currencyServiceMock.getAllCurrencies()).willReturn(null);
    List<CurrencyDto> actualList = this.currencyFacade.getAllCurrenciesDto();
    BDDAssertions.then(actualList.isEmpty()).isTrue();
  }

  @Test(expected = PageNotFoundException.class)
  public void getCurrencyById_givenCurrencyNotFound_shouldThrowPageNotFoundException() {
    // given:
    given(this.currencyServiceMock.getByID(5)).willReturn(null);
    // when:
    this.currencyFacade.getCurrencyDtoById(5);
  }

  @Test
  public void getCurrencyById_givenCurrencyFound_shouldReturnCurrencyDto() {
    // given:
    this.initCurrency1();
    given(this.currencyServiceMock.getByID(5)).willReturn(this.currency1);
    // when:
    CurrencyDto actualValue = this.currencyFacade.getCurrencyDtoById(5);
    // then:
    this.testCurrency(this.currency1, actualValue);
  }

  @Test
  public void saveNew_givenCurrencyDtoIsFilled_shouldAddCurrency() {
    // given:
    CurrencyDto currencyDto = this.getCurrencyDto();
    Currency currency = this.modelMapper.map(currencyDto, Currency.class);
    // when:
    this.currencyFacade.saveNew(currencyDto);
    // then:
    BDDMockito.then(this.currencyServiceMock).should().add(refEq(currency));
  }

  private CurrencyDto getCurrencyDto() {
    CurrencyDto currencyDto = new CurrencyDto();
    currencyDto.setName("name");
    currencyDto.setCharCode("char-code");
    currencyDto.setCode("code");
    currencyDto.setId(1);
    return currencyDto;
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveNew_givenCurrencyDtoIsNull_expectIllegalArgumentException() {
    this.currencyFacade.saveNew(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_givenCurrencyDtoIsNull_expectIllegalArgumentException() {
    this.currencyFacade.update(null);
  }

  @Test
  public void update_givenCurrencyDto_shouldUpdateCurrency() {
    // given:
    CurrencyDto currencyDto = this.getCurrencyDto();
    Currency currency = this.modelMapper.map(currencyDto, Currency.class);
    // when:
    this.currencyFacade.update(currencyDto);
    // then:
    BDDMockito.then(this.currencyServiceMock).should().update(currency);
  }

  @Test
  public void delete_givenId_thenShouldDeleteById() {
    // when:
    this.currencyFacade.delete(5);
    // then:
    BDDMockito.then(this.currencyServiceMock).should().delete(5);
  }
}