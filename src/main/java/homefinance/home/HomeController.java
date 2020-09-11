package homefinance.home;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(HomeController.URL)
public class HomeController {
  public static final String URL = "/";
  public static final String USERNAME_ATTRIBUTE = "username";

  @GetMapping()
  public String home() {
    return "home";
  }

  @GetMapping(value = "/access-denied")
  public String accessDenied(Principal user, Model model) {
    model.addAttribute(USERNAME_ATTRIBUTE, user.getName());
    return "access-denied";
  }

}
