package ruslan.macari.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.CurrencyService;
import ruslan.macari.util.Action;
import ruslan.macari.util.PathSelector;

@Controller
@RequestMapping(CurrenciesController.URL)
public class CurrenciesController {
 
    public static final String URL = "/currencies";
    public static final String LIST_PATH = URL + "/list";
    public static final String NEW = "/new";
    public static final String NEW_PATH = URL + NEW;
    public static final String REDIRECT_PATH = "redirect:" + URL;
    public static final String VIEW_PATH = URL + "/view";
    
    private CurrencyService currencyService;
    private PathSelector pathSelector;

    @Autowired
    public void setPathSelector(PathSelector pathSelector) {
        this.pathSelector = pathSelector;
    }

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
        Action action = () -> currencyService.add(currency);
        return pathSelector.setActionOk(action).setPaths(REDIRECT_PATH, NEW_PATH).setErrors(result).getPath();
    }
    
    @GetMapping(value = "/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        Currency currency = currencyService.getByID(id);
        model.addAttribute("currency", currency);
        return "/users/view";//VIEW_PATH;
    }
    
}
