package homefinance.money.currency;

import static homefinance.common.CommonController.addModelToRedirectAttributes;
import static homefinance.common.CommonController.getRedirectURL;
import static homefinance.common.CommonController.isRedirectAndFlashModelMerged;

import homefinance.common.PossibleDuplicationException;
import homefinance.common.PossibleDuplicationExceptionViewNameInRequestBuffer;
import homefinance.common.RequestBuffer;
import homefinance.money.currency.dto.CurrencyDto;
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
public class CurrencyController {

  public static final String URL = "/currencies";
  public static final String CURRENCY_ATTRIBUTE_NAME = "currency";

  private final CurrencyFacade currencyFacade;
  private final RequestBuffer requestBuffer;

  @Autowired
  public CurrencyController(CurrencyFacade currencyFacade, RequestBuffer request) {
    this.currencyFacade = currencyFacade;
    requestBuffer = request;
  }

  @GetMapping()
  public String list(Model model) {
    model.addAttribute("currencies", currencyFacade.getAllCurrenciesDto());
    return "currencies/list";
  }

  @GetMapping("/new")
  public String openNew(Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute(CURRENCY_ATTRIBUTE_NAME, new CurrencyDto());
    }
    return "currencies/new";
  }

  @PossibleDuplicationException(viewName = "/currencies/new")
  @PostMapping("/new")
  public String saveNew(@Valid @ModelAttribute(CURRENCY_ATTRIBUTE_NAME) CurrencyDto currencyDto,
      BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
    if (errors.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL("/currencies/new");
    }
    currencyFacade.saveNew(currencyDto);
    return getRedirectURL(URL);
  }

  @GetMapping("/{id}")
  public String view(@PathVariable("id") Integer id, Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute(CURRENCY_ATTRIBUTE_NAME, currencyFacade.getCurrencyDtoById(id));
    }
    return "currencies/view";
  }

  @PossibleDuplicationExceptionViewNameInRequestBuffer
  @PostMapping("/update")
  public String update(@Valid @ModelAttribute(CURRENCY_ATTRIBUTE_NAME) CurrencyDto currencyDto,
      BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
    if (errors.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL(getCurrencyViewName(currencyDto));
    }
    requestBuffer.setViewName(getCurrencyViewName(currencyDto));
    currencyFacade.update(currencyDto);
    return getRedirectURL(getCurrencyViewName(currencyDto));
  }

  private String getCurrencyViewName(CurrencyDto currencyDto) {
    return URL + '/' + currencyDto.getId();
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    currencyFacade.delete(id);
    return getRedirectURL(URL);
  }

  @GetMapping("/fill")
  public String fillCurrencies() {
    currencyFacade.fillDistinctCurrencies();
    return getRedirectURL(URL);
  }

}
