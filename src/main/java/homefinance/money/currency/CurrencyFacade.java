package homefinance.money.currency;

import homefinance.common.exception.PageNotFoundException;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CurrencyFacade {

  private final CurrencyService currencyService;
  private final ModelMapper modelMapper;

  public CurrencyFacade(CurrencyService currencyService, ModelMapper modelMapper) {
    this.currencyService = currencyService;
    this.modelMapper = modelMapper;
  }

  public List<CurrencyDto> getAllCurrenciesDto() {
    log.debug("getAllCurrenciesDto()");
    List<Currency> allCurrencies = currencyService.getAllCurrencies();
    if (CollectionUtils.isEmpty(allCurrencies)) {
      log.debug("No currencies found");
      return new ArrayList<>();
    }
    return allCurrencies.stream()
        .map(this::currencyToDto)
        .collect(Collectors.toList());
  }

  private CurrencyDto currencyToDto(Currency currency) {
    return modelMapper.map(currency, CurrencyDto.class);
  }

  public CurrencyDto getCurrencyDtoById(int id) {
    return currencyToDto(getCurrencyById(id));
  }

  private Currency getCurrencyById(int id) {
    Currency currency = currencyService.getByID(id);
    if (Objects.isNull(currency)) {
      log.error("Currency by id={} not found", id);
      throw new PageNotFoundException();
    }
    return currency;
  }

  public void saveNew(CurrencyDto currencyDto) {
    validateCurrencyDto(currencyDto);
    currencyService.add(mapToCurrency(currencyDto));
  }

  private Currency mapToCurrency(CurrencyDto currencyDto) {
    return modelMapper.map(currencyDto, Currency.class);
  }

  private void validateCurrencyDto(CurrencyDto currencyDto) {
    Assert.notNull(currencyDto, "currencyDto must be filled");
  }

  public void update(CurrencyDto currencyDto) {
    validateCurrencyDto(currencyDto);
    currencyService.update(mapToCurrency(currencyDto));
  }

  public void delete(int id) {
    currencyService.delete(id);
  }

  public void fillDistinctCurrencies() {
    currencyService.fillDistinctCurrencies();
  }
}
