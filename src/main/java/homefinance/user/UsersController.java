package homefinance.user;

import homefinance.common.CommonController;
import homefinance.common.exception.PageNotFoundException;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(UsersController.URL)
public class UsersController extends CommonController<User> {

  public static final String URL = "/users";
  public static final String NEW = "/new";
  public static final String NEW_PATH = URL + NEW;
  public static final String REDIRECT_PATH = "redirect:" + URL;
  public static final String VIEW_PATH = URL + "/view";

  private UserService userService;
  private PasswordEncoder encoder;
  private String rootname;

  @Value("${db.username}")
  public void setRootname(String rootname) {
    this.rootname = rootname;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setEncoder(PasswordEncoder encoder) {
    this.encoder = encoder;
  }

  @GetMapping()
  public String openList(Model model) {
    List<User> users = this.userService.usersExceptRoot();
    model.addAttribute("users", users);
    return "users/list";
  }

  @GetMapping(value = "/{id}")
  public String view(@PathVariable("id") Integer id, Model model) {
    User user = this.userService.getById(id);
    this.testUser(user);
    model.addAttribute("user", user);
    return VIEW_PATH;
  }

  private void testUser(User user) {
    this.test(user);
    if (user.getName().equals(this.rootname)) {
      throw new PageNotFoundException();
    }
  }

  @PostMapping(value = "/{id}")
  public String update(@Valid @ModelAttribute("user") User user, BindingResult result,
      @PathVariable("id") Integer id,
      @RequestParam(value = "changePassword", defaultValue = "false") boolean changePassword,
      @RequestParam(value = "admin", defaultValue = "false") boolean admin) {
    if (result.hasErrors()) {
      return VIEW_PATH;
    }
    Runnable action = () -> this.userService.update(this.getUser(id, user, changePassword, admin));
    return this.pathSelector
        .setActionOk(action)
        .setPaths(REDIRECT_PATH, VIEW_PATH)
        .setErrors(result)
        .getPath();
  }

  private User getUser(Integer id, User user, boolean changePassword, boolean admin) {
    User foundUser = this.userService.getById(id);
    foundUser.setName(user.getName());
    if (changePassword) {
      foundUser.setPassword(this.encoder.encode(user.getPassword()));
    }
    foundUser.setOneRole(admin ? Role.ADMIN : Role.USER);
    return foundUser;
  }

  @GetMapping(NEW)
  public String newUser(Model model) {
    Object flashModel = model.asMap().get("model");
    if (Objects.isNull(flashModel)) {
      model.addAttribute("user", new User());
    } else {
      model.mergeAttributes(((Model) flashModel).asMap());
    }
    return NEW_PATH;
  }

  @PostMapping(NEW)
  public String save(@Valid @ModelAttribute("user") User user, BindingResult result,
      @RequestParam(value = "admin", defaultValue = "false") boolean admin,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("model", model);
      return getRedirectURL(NEW_PATH);
    }
    return this.pathSelector
        .setActionOk(() -> this.add(user, admin))
        .setPaths(REDIRECT_PATH, NEW_PATH)
        .setErrors(result)
        .getPath();
  }

  private void add(User user, boolean admin) {
    user.setPassword(this.encoder.encode(user.getPassword()));
    user.setEnabled(true);
    user.setOneRole(admin ? Role.ADMIN : Role.USER);
    this.userService.add(user);
  }

  @DeleteMapping(value = "/{id}")
  public String deleteUser(@PathVariable("id") Integer id) {
    this.userService.delete(id);
    return REDIRECT_PATH;
  }

}
