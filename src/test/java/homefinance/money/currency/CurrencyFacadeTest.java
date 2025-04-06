package homefinance.money.currency;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;

import homefinance.common.exception.PageNotFoundException;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CurrencyFacadeTest {

  private static final CurrencyDto CURRENCY_DTO = new CurrencyDto()
      .setName("name")
      .setCharCode("char-code")
      .setCode("code")
      .setId(1);
  @Mock
  private CurrencyService currencyServiceMock;
  private static final Currency CURRENCY_1 = new Currency()
      .setId(1)
      .setCharCode("char1")
      .setCode("code1")
      .setName("name1");
  private static final Currency CURRENCY_2 = new Currency()
      .setId(1)
      .setCharCode("char2")
      .setCode("code2")
      .setName("name2");
  private CurrencyFacade currencyFacade;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = new ModelMapper();
    currencyFacade = new CurrencyFacade(currencyServiceMock, modelMapper);
  }

  @Test
  void getAllCurrencies_givenCurrencyServiceWillReturnTwoCurrencies_shouldReturnListWith2CurrencyDto() {
    //given
    given(currencyServiceMock.getAllCurrencies())
        .willReturn(Arrays.asList(CURRENCY_1, CURRENCY_2));
    //when
    List<CurrencyDto> actualCurrenciesDto = currencyFacade.getAllCurrenciesDto();
    //then
    BDDMockito.then(currencyServiceMock).should().getAllCurrencies();
    BDDAssertions.then(actualCurrenciesDto.size()).isEqualTo(2);
    testCurrency(CURRENCY_1, actualCurrenciesDto.get(0));
    testCurrency(CURRENCY_2, actualCurrenciesDto.get(1));
  }

  private void testCurrency(Currency currency1, CurrencyDto currencyDto1) {
    BDDAssertions.then(currencyDto1).usingRecursiveComparison().isEqualTo(currency1);
  }

  @Test
  void getAllCurrenciesDto_givenCurrencyServiceWillReturnNull_shouldReturnEmptyList() {
    given(currencyServiceMock.getAllCurrencies()).willReturn(null);
    List<CurrencyDto> actualList = currencyFacade.getAllCurrenciesDto();
    BDDAssertions.then(actualList.isEmpty()).isTrue();
  }

  @Test()
  void getCurrencyById_givenCurrencyNotFound_shouldThrowPageNotFoundException() {
    // given:
    given(currencyServiceMock.getByID(5)).willReturn(null);
    // when:
    assertThrows(PageNotFoundException.class, () -> currencyFacade.getCurrencyDtoById(5));
  }

  @Test
  void getCurrencyById_givenCurrencyFound_shouldReturnCurrencyDto() {
    // given:
    given(currencyServiceMock.getByID(5)).willReturn(CURRENCY_1);
    // when:
    CurrencyDto actualValue = currencyFacade.getCurrencyDtoById(5);
    // then:
    testCurrency(CURRENCY_1, actualValue);
  }

  @Test
  void saveNew_givenCurrencyDtoIsFilled_shouldAddCurrency() {
    // given:
    Currency currency = modelMapper.map(CURRENCY_DTO, Currency.class);
    // when:
    currencyFacade.saveNew(CURRENCY_DTO);
    // then:
    BDDMockito.then(currencyServiceMock).should().add(refEq(currency));
  }

  @Test()
  void saveNew_givenCurrencyDtoIsNull_expectIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> currencyFacade.saveNew(null));
  }

  @Test()
  void update_givenCurrencyDtoIsNull_expectIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> currencyFacade.update(null));
  }

  @Test
  void update_givenCurrencyDto_shouldUpdateCurrency() {
    // given:
    CurrencyDto currencyDto = CURRENCY_DTO;
    Currency currency = modelMapper.map(currencyDto, Currency.class);
    // when:
    currencyFacade.update(currencyDto);
    // then:
    BDDMockito.then(currencyServiceMock).should().update(currency);
  }

  @Test
  void delete_givenId_thenShouldDeleteById() {
    // when:
    currencyFacade.delete(5);
    // then:
    BDDMockito.then(currencyServiceMock).should().delete(5);
  }

  @Test
  void fillDistinctCurrencies_shouldInvokeFillDistinctCurrencies() {
    // when:
    currencyFacade.fillDistinctCurrencies();
    // then:
    BDDMockito.then(currencyServiceMock).should().fillDistinctCurrencies();
  }
}
