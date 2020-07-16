package homefinance.user;

import homefinance.common.CommonController;
import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.exception.PageNotFoundException;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
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

  private final UserService userService;
  private final PasswordEncoder encoder;
  private String rootname;

  @Autowired
  public UsersController(UserService userService, PasswordEncoder encoder) {
    this.userService = userService;
    this.encoder = encoder;
  }

  @Value("${db.username}")
  public void setRootname(String rootname) {
    this.rootname = rootname;
  }

  @GetMapping()
  public String openList(Model model) {
    List<User> users = userService.usersExceptRoot();
    model.addAttribute("users", users);
    return "users/list";
  }

  @GetMapping("/{id}")
  public String openView(@PathVariable("id") Integer id, Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      User user = userService.getById(id);
      testUser(user);
      model.addAttribute("user", user);
    }
    return "users/view";
  }

  private void testUser(User user) {
    test(user);
    if (user.getName().equals(rootname)) {
      throw new PageNotFoundException();
    }
  }

  @PostMapping("/{id}")
  public String update(@Valid @ModelAttribute("user") User user, BindingResult result,
      @PathVariable("id") int id,
      @RequestParam(value = "changePassword", defaultValue = "false") boolean changePassword,
      @RequestParam(value = "admin", defaultValue = "false") boolean isAdmin,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL(URL + '/' + id);
    }
    try {
      userService.update(getUser(id, user, changePassword, isAdmin));
      return getRedirectURL(URL);
    } catch (DuplicateFieldsException ex) {
      result.rejectValue(ex.getField(), ex.getErrorCode());
      return getRedirectURL(URL + '/' + id);
    }
  }

  private User getUser(Integer id, User user, boolean changePassword, boolean isAdmin) {
    User foundUser = userService.getById(id);
    foundUser.setName(user.getName());
    if (changePassword) {
      foundUser.setPassword(encoder.encode(user.getPassword()));
    }
    foundUser.setOneRole(isAdmin ? Role.ADMIN : Role.USER);
    return foundUser;
  }

  @GetMapping("/new")
  public String newUser(Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute("user", new User());
    }
    return "users/new";
  }

  @PostMapping("/new")
  public String save(@Valid @ModelAttribute("user") User user, BindingResult result,
      @RequestParam(value = "admin", defaultValue = "false") boolean admin,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL("/users/new");
    }
    return pathSelector
        .setActionOk(() -> add(user, admin))
        .setPaths(getRedirectURL(URL), "/users/new")
        .setErrors(result)
        .getPath();
  }

  private void add(User user, boolean admin) {
    user.setPassword(encoder.encode(user.getPassword()));
    user.setEnabled(true);
    user.setOneRole(admin ? Role.ADMIN : Role.USER);
    userService.add(user);
  }

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable("id") Integer id) {
    userService.delete(id);
    return getRedirectURL(URL);
  }

}
