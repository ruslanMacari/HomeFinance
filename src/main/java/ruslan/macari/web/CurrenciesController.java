package ruslan.macari.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ruslan.macari.domain.Currency;
import ruslan.macari.security.User;
import ruslan.macari.service.CurrencyService;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Controller
@RequestMapping(CurrenciesController.URL)
public class CurrenciesController {
 
    public static final String URL = "/currencies";
    public static final String LIST_PATH = URL + "/list";
    public static final String NEW = "/new";
    public static final String NEW_PATH = URL + NEW;
    public static final String REDIRECT_PATH = "redirect:" + URL;
    private CurrencyService currencyService;

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    @GetMapping()
    public String list(Model model) {
        List<Currency> currencies = currencyService.list();
        model.addAttribute("currencies", currencies);
        return LIST_PATH;
    }
    
    @GetMapping(NEW)
    public String newCurrency(Model model) {
        model.addAttribute("newCurrency", new Currency());
        return NEW_PATH;
    }
    
    @PostMapping(NEW)
    public String save(@Valid @ModelAttribute("newCurrency") Currency currency, BindingResult result) {
        if (result.hasErrors()) {
            return NEW_PATH;
        }
        try {
            currencyService.add(currency);
            return REDIRECT_PATH;
        } catch (DuplicateFieldsException ex) {
            result.rejectValue(ex.getField(), ex.getErrorCode());
            return NEW_PATH;
        }
    }
    
}
