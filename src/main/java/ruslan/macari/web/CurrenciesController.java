package ruslan.macari.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.domain.Currency;
import ruslan.macari.security.User;
import ruslan.macari.service.CurrencyService;

@Controller
@RequestMapping("/currencies")
public class CurrenciesController {
 
    private CurrencyService currencyService;

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    @GetMapping()
    public String list(Model model) {
        List<Currency> currencies = currencyService.list();
        model.addAttribute("currencies", currencies);
        return "currencies/list";
    }
    
    @GetMapping(value = "/new")
    public String newCurrency(Model model) {
        model.addAttribute("newCurrency", new Currency());
        return "currencies/new";
    }
    
}
