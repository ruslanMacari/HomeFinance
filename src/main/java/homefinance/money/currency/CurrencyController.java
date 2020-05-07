package homefinance.money.currency;

import homefinance.common.CommonController;
import homefinance.common.RequestBuffer;
import homefinance.common.exception.DuplicateFieldsException;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(CurrencyController.URL)
public class CurrencyController extends CommonController<Currency> {

  public static final String URL = "/currencies";
  public static final String CURRENCY_ATTRIBUTE_NAME = "currency";

  private final CurrencyService currencyService;
  private final CurrencyFacade currencyFacade;
  private final RequestBuffer requestBuffer;

  @Autowired
  public CurrencyController(CurrencyService currencyService, CurrencyFacade currencyFacade,
      RequestBuffer request) {
    this.currencyService = currencyService;
    this.currencyFacade = currencyFacade;
    this.requestBuffer = request;
  }

  // TODO: 23.02.2020 RMACARI: refactor to avoid using entities on views, use models

  @GetMapping()
  public String list(Model model) {
    model.addAttribute("currencies", this.currencyFacade.getAllCurrenciesDto());
    return "currencies/list";
  }

  @GetMapping("/new")
  public String openNew(Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute(CURRENCY_ATTRIBUTE_NAME, new CurrencyDto());
    }
    return "currencies/new";
  }

  @PostMapping("/new")
  public String saveNew(@Valid @ModelAttribute(CURRENCY_ATTRIBUTE_NAME) Currency currency,
      BindingResult error, RedirectAttributes redirectAttributes, Model model) {
    if (error.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL("/currencies/new");
    }
    this.requestBuffer.setViewNameAndErrors("currencies/new", error);
    this.currencyService.add(currency);
    return getRedirectURL(URL);
  }

  @GetMapping("/{id}")
  public String view(@PathVariable("id") Integer id, Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute(CURRENCY_ATTRIBUTE_NAME, this.currencyFacade.getCurrencyDtoById(id));
    }
    return "currencies/view";
  }

  @PostMapping("/update")
  public String update(@Valid @ModelAttribute(CURRENCY_ATTRIBUTE_NAME) Currency currency,
      BindingResult result, RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return this.getRedirectToCurrencyView(currency);
    }
    // TODO: 17.04.2020 RMACARI: move this to facade layer
    try {
      this.currencyService.update(currency);
      return getRedirectURL(URL);
    } catch (DuplicateFieldsException ex) {
      result.rejectValue(ex.getField(), ex.getErrorCode());
      return this.getRedirectToCurrencyView(currency);
    }
  }

  private String getRedirectToCurrencyView(Currency currency) {
    return getRedirectURL(URL + '/' + currency.getId());
  }

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable("id") Integer id) {
    this.currencyService.delete(id);
    return getRedirectURL(URL);
  }

  @GetMapping("/fill")
  public String fillCurrencies() {
    this.currencyService.fillDistinctCurrencies();
    return getRedirectURL(URL);
  }

}
