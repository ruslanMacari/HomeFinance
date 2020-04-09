package homefinance.money.currency;

import homefinance.money.currency.entity.Currency;
import homefinance.common.CommonController;
import java.util.List;
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

@Controller
@RequestMapping(CurrencyController.URL)
public class CurrencyController extends CommonController<Currency> {

  public static final String URL = "/currencies";
  public static final String NEW_URL = "/new";
  public static final String REDIRECT_URL = "redirect:" + URL;

  private CurrencyService currencyService;

  public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  // TODO: 23.02.2020 RMACARI: refactor to avoid using entities on views, use models

  @GetMapping()
  public String list(Model model) {
    List<Currency> currencies = this.currencyService.list();
    model.addAttribute("currencies", currencies);
    return "currencies/list";
  }

  @GetMapping(NEW_URL)
  public String newCurrency(Model model) {
    model.addAttribute("newCurrency", new Currency());
    return "currencies/new";
  }

  @PostMapping(NEW_URL)
  public String save(@Valid @ModelAttribute("newCurrency") Currency currency,
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

  @GetMapping(value = "/{id}")
  public String view(@PathVariable("id") Integer id, Model model) {
    Currency currency = this.currencyService.getByID(id);
    this.test(currency);
    model.addAttribute("currency", currency);
    return "currencies/view";
  }

  @PostMapping("/update")
  public String update(@Valid @ModelAttribute("currency") Currency currency, BindingResult result) {
    if (result.hasErrors()) {
      return "currencies/view";
    }
    return this.pathSelector
        .setActionOk(() -> this.currencyService.update(currency))
        .setPaths(REDIRECT_URL, "currencies/view")
        .setErrors(result)
        .getPath();
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
