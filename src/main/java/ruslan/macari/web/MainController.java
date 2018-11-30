package ruslan.macari.web;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.domain.Currency;
import ruslan.macari.security.User;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.service.CurrencyService;

@Controller
@RequestMapping("/")
public class MainController {
    
    private CurrencyService currencyService;
    
    private CurrentUser currentUser;

    @Autowired
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
    
    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    @GetMapping()
    public String index(HttpSession session) {
//        if (!currentUser.exists(session.getId())) {
//            return "redirect:/authorization";
//        }
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
    
    @GetMapping(value = "/access-denied")
    public String accesssDenied(Principal user, Model model) {

        if (user != null) {
            model.addAttribute("user", new User (user.getName()));
        } else {
            model.addAttribute("user", new User ("Unauthorized"));
        }
        return "access-denied";

    }

}
