package ruslan.macari.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.dao.CurrencyDAO;
import ruslan.macari.dao.UserDAO;
import ruslan.macari.models.Currency;
import ruslan.macari.models.User;

@Controller
public class MainController {
    
    @Autowired
    private CurrencyDAO currencyDAO;
    
    @RequestMapping(value = "/home")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/createCurrency")
    public String createCurrency(Model model) {
        model.addAttribute("currency", new Currency());
        model.addAttribute("listCurrency", currencyDAO.listCurrency());
        return "createCurrency";
    }
    
    @RequestMapping(value = "/saveCurrency")
    public String saveCurrency(@Valid @ModelAttribute("currency") Currency currency, 
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createCurrency";
        }
        currencyDAO.addCurrency(currency);
        return "redirect:/home";
    }
    
}
