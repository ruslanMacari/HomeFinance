package ruslan.macari.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

@Controller
@RequestMapping("/authorization")
@PropertySource("classpath:app.properties")
public class LoginController {

    private UserService userService;

    @Value("${db.password}")
    private String password;

    @Value("${db.username}")
    private String username;
    
    private CurrentUser currentUser;

    @Autowired
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier("newUserValidator")
    private Validator newUserValidator;

    @Autowired
    @Qualifier("userLoginValidator")
    private Validator userLoginValidator;

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

    @GetMapping()
    public String authorization(HttpSession session, Model model) {
        if (currentUser.exists(session.getId())) {
            return "redirect:/";
        }
        addSimpleUsers(model);
        model.addAttribute("userLogin", new User());
        return "login/authorization";
    }
    
    private void addSimpleUsers(Model model) {
        model.addAttribute("listUsers", userService.getSimpleUsers());
    }

    @GetMapping("/createUser")
    public ModelAndView createUser() {
        return new ModelAndView("login/createUser", "user", new User());
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "login/createUser";
        }
        userService.add(user);
        return "redirect:/authorization";
    }

    @PostMapping()
    public String authorization(@Valid @ModelAttribute("userLogin") User user, BindingResult result, Model model,
            HttpSession session) {
        if (result.hasErrors()) {
            addSimpleUsers(model);
            return "login/authorization";
        }
        addSessionUser(session, user);
        return "redirect:/";
    }
    
    private void addSessionUser(HttpSession session, User user) {
        User userFound = userService.getByNameAndPassword(user.getName(), user.getPassword());
        currentUser.add(session.getId(), userFound);
        session.setAttribute("user", userFound);
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        removeSessionUser(session);
        return "redirect:/authorization";
    }
    
    private void removeSessionUser(HttpSession session) {
        session.removeAttribute("user");
        currentUser.remove(session.getId());
    }

    @InitBinder("userLogin")
    protected void initUserLoginBinder(WebDataBinder dataBinder) {
        setValidator(dataBinder, userLoginValidator);
    }
    
    private void setValidator(WebDataBinder dataBinder, Validator validator) {
        if (dataBinder.getTarget() == null) {
            return;
        }
        dataBinder.setValidator(validator);
    }
    
    @InitBinder("user")
    protected void initUserBinder(WebDataBinder dataBinder) {
        setValidator(dataBinder, newUserValidator);
    }

}
