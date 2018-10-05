package ruslan.macari.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.dao.UserDAO;
import ruslan.macari.dao.UserRepository;
import ruslan.macari.models.User;
import ruslan.macari.models.UserLogin;
import ruslan.macari.validator.UserLoginValidator;
import ruslan.macari.validator.UserValidator;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private UserLoginValidator userLoginValidator;
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(value = "/")
    public String main(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }
    
    @RequestMapping(value = "/createUser")
    public ModelAndView createUser(HttpSession session, Model model) {
        return new ModelAndView("createUser", "user", new User());
    }
    
    @RequestMapping(value = "/saveUser")
    public String saveUser(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createUser";
        }
        userDAO.addUser(user);
        System.out.println("userRepository------------------------");
        System.out.println(userRepository.findByName(user.getName()));
        return "redirect:/login";
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        addListUsers(model);
        model.addAttribute("user", new UserLogin());
        return "login";
    }
    
    private void addListUsers(Model model) {
        model.addAttribute("listUsers", userDAO.listUsers());
        model.addAttribute("listUsersLimited", userDAO.listUsersLimit(3));
    }
    
    @RequestMapping(value = "/checkUser")
    public String checkUser(@Valid @ModelAttribute("user") UserLogin user, BindingResult result, Model model,
            HttpSession session) {
        if (result.hasErrors()) {
            addListUsers(model);
            return "login";
        }
        session.setAttribute("user", userDAO.getUserById(user.getId()));
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
