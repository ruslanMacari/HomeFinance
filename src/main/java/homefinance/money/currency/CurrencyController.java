package homefinance.money.currency;

import homefinance.common.CommonController;
import homefinance.common.exception.DuplicateFieldsException;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import javax.validation.Valid;
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
  public static final String NEW_URL = "/new";
  public static final String REDIRECT_URL = REDIRECT + URL;

  private final CurrencyService currencyService;
  private final CurrencyFacade currencyFacade;

  public CurrencyController(CurrencyService currencyService, CurrencyFacade currencyFacade) {
    this.currencyService = currencyService;
    this.currencyFacade = currencyFacade;
  }

  // TODO: 23.02.2020 RMACARI: refactor to avoid using entities on views, use models

  @GetMapping()
  public String list(Model model) {
    model.addAttribute("currencies", this.currencyFacade.getAllCurrenciesDto());
    return "currencies/list";
  }

  @GetMapping(NEW_URL)
  public String openNew(Model model) {
    model.addAttribute("currency", new CurrencyDto());
    return "currencies/new";
  }

  @PostMapping(NEW_URL)
  public String saveNew(@Valid @ModelAttribute("currency") Currency currency,
      BindingResult result) {
    if (result.hasErrors()) {
      return "currencies/new";
    }
    return this.pathSelector
        .setActionOk(() -> this.currencyService.add(currency))
        .setPaths(REDIRECT_URL, "currencies/new")
        .setErrors(result)
        .getPath();
  }

  @GetMapping("/{id}")
  public String view(@PathVariable("id") Integer id, Model model) {
    Currency currency = this.currencyService.getByID(id);
    this.test(currency);
    Object flashModel = model.asMap().get("flashModel");
    if (flashModel == null) {
      model.addAttribute("currency", currency);
    } else {
      model.mergeAttributes(((Model) flashModel).asMap());
    }
    return "currencies/view";
  }

  @PostMapping("/update")
  public String update(@Valid @ModelAttribute("currency") Currency currency, BindingResult result,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("flashModel", model);
      return this.getRedirectToCurrencyView(currency);
    }
    // TODO: 17.04.2020 RMACARI: move this to facade layer
    try {
      this.currencyService.update(currency);
      return REDIRECT_URL;
    } catch (DuplicateFieldsException ex) {
      result.rejectValue(ex.getField(), ex.getErrorCode());
      return this.getRedirectToCurrencyView(currency);
    }
  }

  private String getRedirectToCurrencyView(@ModelAttribute("currency") @Valid Currency currency) {
    return REDIRECT + "/currencies/" + currency.getId();
  }

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable("id") Integer id) {
    this.currencyService.delete(id);
    return REDIRECT_URL;
  }

  @GetMapping("/fill")
  public String fillCurrencies() {
    this.currencyService.fillDistinctCurrencies();
    return REDIRECT_URL;
  }

}
