package ruslan.macari.web;

import java.security.Principal;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.security.User;

@Controller
@RequestMapping("/")
public class MainController {
    
    @GetMapping()
    public String index(HttpSession session) {
        return "index";
    }

    @GetMapping(value = "/access-denied")
    public String accesssDenied(Principal user, Model model) {
        model.addAttribute("user", new User (user.getName()));
        return "access-denied";
    }

}
