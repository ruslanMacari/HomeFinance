package homefinance.user.login;

import homefinance.common.CommonController;
import homefinance.user.service.UserService;
import homefinance.common.util.PathSelector;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginController {

  public static final String URL = "/login";
  public static final String REDIRECT = "redirect:";
  public static final String REDIRECT_URL = REDIRECT + URL;
  public static final String DIRECTORY = "auth";
  public static final String REGISTRATION = "/registration";
  public static final String REGISTRATION_PATH = DIRECTORY + REGISTRATION;
  public static final String REDIRECT_REGISTRATION = REDIRECT + URL + REGISTRATION;
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  private final UserService userService;
  private final LoginFacade loginFacade;
  private PathSelector pathSelector;

  @Autowired
  public LoginController(UserService userService, LoginFacade loginFacade) {
    this.userService = userService;
    this.loginFacade = loginFacade;
  }

  @Autowired
  public void setPathSelector(PathSelector pathSelector) {
    this.pathSelector = pathSelector;
  }

  @GetMapping()
  public String login(Model model) {
    if (this.loginFacade.isAuthenticated()) {
      // TODO: 22.05.2020 RMACARI: move logging to aspect
      logger.debug("User is authenticated redirect to home");
      return CommonController.getRedirectURL("/");
    }
    model.addAttribute("listUsers", this.userService.getSimpleUsers());
    model.addAttribute("user", new UserLoginDto());
    return "auth/login";
  }

  @GetMapping(REGISTRATION)
  public String registration(Model model) {
    Object flashModel = model.asMap().get("model");
    if (flashModel == null) {
      model.addAttribute("user", new UserLoginDto());
    } else {
      model.mergeAttributes(((Model) flashModel).asMap());
    }
    return REGISTRATION_PATH;
  }

  @PostMapping(REGISTRATION)
  public String registration(@Valid @ModelAttribute("user") UserLoginDto user,
      BindingResult result, RedirectAttributes redirectAttributes, Model model) {
    // TODO: 15.03.2020 RMACARI: test validation
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("model", model);
      return REDIRECT_REGISTRATION;
    }
    return this.pathSelector
        .setActionOk(() -> this.userService.registerUser(user.getName(), user.getPassword()))
        .setActionError(() -> redirectAttributes.addFlashAttribute("model", model))
        .setPaths(REDIRECT_URL, REDIRECT_REGISTRATION)
        .setErrors(result)
        .getPath();
  }

}
