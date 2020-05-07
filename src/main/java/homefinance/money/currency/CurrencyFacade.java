package homefinance.money.currency;

import homefinance.common.exception.PageNotFoundException;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class CurrencyFacade {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyFacade.class);
  private final CurrencyService currencyService;
  private final ModelMapper modelMapper;

  public CurrencyFacade(CurrencyService currencyService, ModelMapper modelMapper) {
    this.currencyService = currencyService;
    this.modelMapper = modelMapper;
  }

  public List<CurrencyDto> getAllCurrenciesDto() {
    logger.debug("getAllCurrenciesDto()");
    List<Currency> currencyList = this.currencyService.getAllCurrencies();
    if (CollectionUtils.isEmpty(currencyList)) {
      logger.debug("this.currencyService.getAllCurrencies() is empty");
      return new ArrayList<>();
    }
    return currencyList.stream()
        .map(this::currencyToDto)
        .collect(Collectors.toList());
  }

  private CurrencyDto currencyToDto(Currency currency) {
    return this.modelMapper.map(currency, CurrencyDto.class);
  }

  public CurrencyDto getCurrencyDtoById(int id) {
    return this.currencyToDto(this.getCurrencyById(id));
  }

  private Currency getCurrencyById(int id) {
    Currency currency = this.currencyService.getByID(id);
    if (Objects.isNull(currency)) {
      logger.error("Currency by id={} not found", id);
      throw new PageNotFoundException();
    }
    return currency;
  }

  public void saveNew(CurrencyDto currencyDto) {
    Assert.notNull(currencyDto, "currencyDto must be filled");
    Currency currency = this.modelMapper.map(currencyDto, Currency.class);
    this.currencyService.add(currency);
  }
}
