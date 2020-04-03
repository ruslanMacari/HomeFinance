package homefinance.home;

import homefinance.common.security.User;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

  @GetMapping()
  public String index() {
    return "index";
  }

  @GetMapping(value = "/access-denied")
  public String accessDenied(Principal user, Model model) {
    model.addAttribute("user", new User(user.getName()));
    return "access-denied";
  }

}
