package ruslan.macari.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.CurrencyService;

@Controller
public class MainController {
    
    private CurrencyService currencyService;

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/createCurrency")
    public String createCurrency(Model model) {
        model.addAttribute("currency", new Currency());
        model.addAttribute("listCurrency", currencyService.list());
        return "createCurrency";
    }
    
    @PostMapping(value = "/saveCurrency")
    public String saveCurrency(@Valid @ModelAttribute("currency") Currency currency, BindingResult result) {
        if (result.hasErrors()) {
            return "createCurrency";
        }
        currencyService.add(currency);
        return "redirect:/home";
    }
    
}
