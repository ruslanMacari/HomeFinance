package ruslan.macari.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.CurrencyService;

@Controller
public class MainController {
    
    @Autowired
    private CurrencyService currencyService;
    
    @RequestMapping(value = "/home")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/createCurrency")
    public String createCurrency(Model model) {
        model.addAttribute("currency", new Currency());
        model.addAttribute("listCurrency", currencyService.listCurrency());
        return "createCurrency";
    }
    
    @RequestMapping(value = "/saveCurrency")
    public String saveCurrency(@Valid @ModelAttribute("currency") Currency currency, 
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createCurrency";
        }
        currencyService.addCurrency(currency);
        return "redirect:/home";
    }
    
}
