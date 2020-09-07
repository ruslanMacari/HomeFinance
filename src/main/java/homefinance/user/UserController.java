package homefinance.user;

import homefinance.common.CommonController;
import homefinance.common.PossibleDuplicationExceptionViewNameInRequestBuffer;
import homefinance.common.RequestBuffer;
import homefinance.common.exception.PageNotFoundException;
import homefinance.user.entity.User;
import java.security.Principal;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(UserController.URL)
public class UserController extends CommonController<User> {

  public static final String URL = "/users";

  private final UserFacade userFacade;
  private final RequestBuffer requestBuffer;
  private String rootname;

  @Autowired
  public UserController(UserFacade userFacade, RequestBuffer requestBuffer) {
    this.userFacade = userFacade;
    this.requestBuffer = requestBuffer;
  }

  @Value("${db.username}")
  public void setRootname(String rootname) {
    this.rootname = rootname;
  }

  @GetMapping()
  public String openList(Model model) {
    model.addAttribute("users", userFacade.getUsersWithoutRoot());
    return "users/list";
  }

  @GetMapping("/{id}")
  public String openView(@PathVariable("id") int id, Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute("user", getUserBy(id));
    }
    return "users/view";
  }

  private UserDto getUserBy(int id) {
    UserDto user = userFacade.getUserById(id);
    checkUser(user);
    return user;
  }

  private void checkUser(UserDto user) {
    if (Objects.isNull(user)
        || user.getName().equals(rootname)) {
      throw new PageNotFoundException();
    }
  }

  @PossibleDuplicationExceptionViewNameInRequestBuffer
  @PostMapping("/{id}")
  public String update(@Valid @ModelAttribute("user") UserDto user, BindingResult result,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL(getUserViewName(user));
    }
    requestBuffer.setViewName(getUserViewName(user));
    userFacade.update(user);
    return getRedirectURL(URL);
  }

  private String getUserViewName(UserDto user) {
    return URL + '/' + user.getId();
  }

  @GetMapping("/new")
  public String newUser(Model model) {
    if (!isRedirectAndFlashModelMerged(model)) {
      model.addAttribute("user", new UserDto());
    }
    return "users/new";
  }

  @PostMapping("/new")
  public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult result,
      RedirectAttributes redirectAttributes, Model model) {
    if (result.hasErrors()) {
      addModelToRedirectAttributes(model, redirectAttributes);
      return getRedirectURL("/users/new");
    }
    return pathSelector
        .setActionOk(() -> userFacade.addUser(user))
        .setPaths(getRedirectURL(URL), "users/new")
        .setErrors(result)
        .getPath();
  }

  @DeleteMapping
  public String deleteUser(@ModelAttribute("user") UserDto userToBeDeleted, Principal principal) {
    // TODO: 11.08.2020 RMACARI: move to facade
    if (principal.getName().equals(userToBeDeleted.getName())) {
      throw new RuntimeException("Can't delete logged in user");
    }
    userFacade.deleteUser(userToBeDeleted.getId());
    return getRedirectURL(URL);
  }

}
