package homefinance.user.login;

import static homefinance.common.CommonController.isRedirectAndFlashModelMerged;

import homefinance.common.CommonController;
import homefinance.common.PossibleDuplicationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(LoginController.URL)
@Slf4j
public class LoginController {

  public static final String URL = "/login";

  private final LoginFacade loginFacade;

  @Autowired
  public LoginController(LoginFacade loginFacade) {
    this.loginFacade = loginFacade;
  }

  @GetMapping()
  public String login(Model model) {
    if (loginFacade.isAuthenticated()) {
      log.debug("User is authenticated redirect to home");
      return CommonController.getRedirectURL("/");
    }
    model.addAttribute("userNames", loginFacade.getSimpleUsersNames());
    model.addAttribute("user", new UserLoginDto());
    return "auth/login";
  }

  @GetMapping("/registration")
  public String openRegistration(Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute("user", new UserLoginDto());
    }
    return "auth/registration";
  }

  @PossibleDuplicationException(viewName = URL + "/registration")
  @PostMapping("/registration")
  public String registerUser(@Valid @ModelAttribute("user") UserLoginDto user,
      BindingResult errors,
      RedirectAttributes redirectAttributes,
      Model model) {
    if (errors.hasErrors()) {
      CommonController.addModelToRedirectAttributes(model, redirectAttributes);
      return CommonController.getRedirectURL(URL + "/registration");
    }
    loginFacade.registerUser(user);
    return CommonController.getRedirectURL(URL);
  }

}
