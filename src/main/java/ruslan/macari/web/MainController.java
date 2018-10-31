package ruslan.macari.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.domain.Currency;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.service.CurrencyService;

@Controller
@RequestMapping("/")
public class MainController {
    
    private CurrencyService currencyService;

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    @GetMapping()
    public String index(HttpSession session) {
        if (!CurrentUser.exists(session.getId())) {
            return "redirect:/authorization";
        }
        return "index";
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
        return "redirect:/";
    }
    
}
