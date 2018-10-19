package ruslan.macari.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.domain.User;
import ruslan.macari.domain.UserLogin;
import ruslan.macari.service.UserService;
import ruslan.macari.validator.UserLoginValidator;
import ruslan.macari.validator.UserValidator;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private UserLoginValidator userLoginValidator;
    
    private Validator validator;

    public LoginController() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @GetMapping(value = "/")
    public String main(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }
    
    @GetMapping(value = "/createUser")
    public ModelAndView createUser() {
        return new ModelAndView("createUser", "user", new User());
    }
    
    @GetMapping(value = "/saveUser")
    public String saveUser(@Valid @ModelAttribute("user")  User user, BindingResult result) {
        if (result.hasErrors()) {
            return "createUser";
        }
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        addListUsers(model);
        model.addAttribute("user", new UserLogin());
        return "login";
    }
    
    private void addListUsers(Model model) {
        model.addAttribute("listUsers", userService.listUsers());
        model.addAttribute("listUsersLimited", userService.listUsersLimit(3));
    }
    
    @GetMapping(value = "/checkUser")
    public String checkUser(@Valid @ModelAttribute("user") UserLogin user, BindingResult result, Model model,
            HttpSession session) {
        if (result.hasErrors()) {
            addListUsers(model);
            return "login";
        }
        session.setAttribute("user", userService.getUserById(user.getId()));
        return "redirect:/home";
    }
    
   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
       Object target = dataBinder.getTarget();
       if (target == null) {
           return;
       }
       LOGGER.info("Target=" + target);
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
