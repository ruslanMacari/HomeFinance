package homefinance.web;

import homefinance.service.UserService;
import homefinance.user.UserLoginModel;
import homefinance.util.PathSelector;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public static final String REDIRECT_ROOT = REDIRECT + "/";
  public static final String REDIRECT_URL = REDIRECT + URL;
  public static final String DIRECTORY = "/auth";
  public static final String URL_PATH = DIRECTORY + URL;
  public static final String REGISTRATION = "/registration";
  public static final String REGISTRATION_PATH = DIRECTORY + REGISTRATION;
  public static final String REDIRECT_REGISTRATION = REDIRECT + URL + REGISTRATION;

  private UserService userService;
  private PathSelector pathSelector;

  @Autowired
  public void setPathSelector(PathSelector pathSelector) {
    this.pathSelector = pathSelector;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public String login(Model model) {
    if (this.isAuthenticated()) {
      return REDIRECT_ROOT;
    }
    model.addAttribute("listUsers", this.userService.getSimpleUsers());
    model.addAttribute("user", new UserLoginModel());
    return URL_PATH;
  }

  private boolean isAuthenticated() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null
        && auth.isAuthenticated()
        && !(auth instanceof AnonymousAuthenticationToken);
  }

  @GetMapping(REGISTRATION)
  public String registration(Model model) {
    Object flashModel = model.asMap().get("model");
    if (flashModel == null) {
      model.addAttribute("user", new UserLoginModel());
    } else {
      model.mergeAttributes(((Model) flashModel).asMap());
    }
    return REGISTRATION_PATH;
  }

  @PostMapping(REGISTRATION)
  public String registration(@Valid @ModelAttribute("user") UserLoginModel user,
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
