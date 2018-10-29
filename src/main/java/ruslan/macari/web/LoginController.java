package ruslan.macari.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.domain.User;
import ruslan.macari.domain.UserLogin;
import ruslan.macari.service.UserService;
import ruslan.macari.web.validator.UserLoginValidator;
import ruslan.macari.web.validator.UserValidator;

@Controller
@RequestMapping("/login")
@PropertySource("classpath:app.properties")
public class LoginController {

    private UserService userService;

    @Value("${db.password}")
    private String password;

    @Value("${db.username}")
    private String username;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserLoginValidator userLoginValidator;

    @PostConstruct
    public void init() {
        createAdmin();
    }

    private void createAdmin() {
        if (userService.getAdmin() != null) {
            return;
        }
        User admin = new User();
        admin.setName(username);
        admin.setPassword(password);
        admin.setAdmin(true);
        userService.add(admin);
    }

    @GetMapping("/authorization")
    public String authorization(HttpSession session, Model model) {
        if (CurrentUser.exists(session.getId())) {
            return "redirect:/";
        }
        addListUsers(model);
        model.addAttribute("user", new UserLogin());
        return "login/authorization";
    }

    @GetMapping("/createUser")
    public ModelAndView createUser() {
        return new ModelAndView("login/createUser", "user", new User());
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "login/createUser";
        }
        userService.add(user);
        return "redirect:/login/authorization";
    }

    private void addListUsers(Model model) {
        model.addAttribute("listUsers", userService.getSimpleUsers());
        model.addAttribute("listUsersLimited", userService.listLimit(3));
    }

    @PostMapping("/authorization")
    public String authorization(@Valid @ModelAttribute("user") UserLogin user, BindingResult result, Model model,
            HttpSession session) {
        if (result.hasErrors()) {
            addListUsers(model);
            return "login/authorization";
        }
        User userFound = userService.getByNameAndPassword(user.getName(), user.getPassword());
        CurrentUser.add(session.getId(), userFound);
        session.setAttribute("user", userFound);
        return "redirect:/";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        CurrentUser.remove(session.getId());
        return "redirect:/login/authorization";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        setValidator(dataBinder, target);
    }

    private void setValidator(WebDataBinder dataBinder, Object target) {
        Class targetClass = target.getClass();
        if (targetClass == User.class) {
            dataBinder.setValidator(userValidator);
        } else if (targetClass == UserLogin.class) {
            dataBinder.setValidator(userLoginValidator);
        }
    }

}
