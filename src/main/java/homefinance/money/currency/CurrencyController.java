package homefinance.money.currency;

import static homefinance.common.CommonController.addModelToRedirectAttributes;
import static homefinance.common.CommonController.getRedirectURL;
import static homefinance.common.CommonController.isRedirectAndFlashModelMerged;

import homefinance.common.HandleDuplicationException;
import homefinance.common.RequestBuffer;
import homefinance.money.currency.dto.CurrencyDto;
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
public class CurrencyController {

  public static final String URL = "/currencies";
  public static final String CURRENCY_ATTRIBUTE_NAME = "currency";

  private final CurrencyFacade currencyFacade;
  private final RequestBuffer requestBuffer;

  @Autowired
  public CurrencyController(CurrencyFacade currencyFacade, RequestBuffer request) {
    this.currencyFacade = currencyFacade;
    this.requestBuffer = request;
  }

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

  @HandleDuplicationException(url = "/currencies/new")
  @PostMapping("/new")
  public String saveNew(@ModelAttribute(CURRENCY_ATTRIBUTE_NAME) CurrencyDto currencyDto,
      BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
    if (errors.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL("/currencies/new");
    }
    this.currencyFacade.saveNew(currencyDto);
    return getRedirectURL(URL);
  }

  @GetMapping("/{id}")
  public String view(@PathVariable("id") Integer id, Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute(CURRENCY_ATTRIBUTE_NAME, this.currencyFacade.getCurrencyDtoById(id));
    }
    return "currencies/view";
  }

  @HandleDuplicationException
  @PostMapping("/update")
  public String update(@ModelAttribute(CURRENCY_ATTRIBUTE_NAME) CurrencyDto currencyDto,
      BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
    if (errors.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL(this.getCurrencyViewUrl(currencyDto));
    }
    this.requestBuffer.setUrl(this.getCurrencyViewUrl(currencyDto));
    this.currencyFacade.update(currencyDto);
    return getRedirectURL(this.getCurrencyViewUrl(currencyDto));
  }

  private String getCurrencyViewUrl(CurrencyDto currencyDto) {
    return URL + '/' + currencyDto.getId();
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    this.currencyFacade.delete(id);
    return getRedirectURL(URL);
  }

  @GetMapping("/fill")
  public String fillCurrencies() {
    this.currencyFacade.fillDistinctCurrencies();
    return getRedirectURL(URL);
  }

}
