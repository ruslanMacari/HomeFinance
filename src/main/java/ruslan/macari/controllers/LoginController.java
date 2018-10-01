package ruslan.macari.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.dao.UserDAO;
import ruslan.macari.models.User;
import ruslan.macari.models.UserLogin;
import ruslan.macari.dao.impl.UserDAOImpl;
import ruslan.macari.validator.UserLoginValidator;
import ruslan.macari.validator.UserValidator;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private UserDAO userDAO;
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private UserLoginValidator userLoginValidator;
    
    @Autowired(required = true)
    @Qualifier(value = "userDAO")
    public void setUserDAO(UserDAO us) {
        this.userDAO = us;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user != null) {
            return "home";
        }
        initLogin(model);
        return "login";
    }
    
    private void initLogin(Model model) {
        model.addAttribute("listUsers", userDAO.listUsers());
        model.addAttribute("listUsersLimited", userDAO.listUsersLimit(5));
        model.addAttribute("user", new UserLogin());
    }
    
    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView createUser(HttpSession session, Model model) {
        return new ModelAndView("createUser", "user", new User());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createUser";
        }
        userDAO.addUser(user);
        initLogin(model);
        return "login";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String home(@Valid @ModelAttribute("user") UserLogin user, BindingResult result, Model model,
            HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("listUsers", userDAO.listUsers());
            model.addAttribute("listUsersLimited", userDAO.listUsersLimit(5));
            return "login";
        }
        session.setAttribute("user", userDAO.getUserById(user.getId()));
        model.addAttribute("listUsers", userDAO.listUsers());
        return "home";
    }

   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
       Object target = dataBinder.getTarget();
       if (target == null) {
           return;
       }
       System.out.println("Target=" + target);
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
