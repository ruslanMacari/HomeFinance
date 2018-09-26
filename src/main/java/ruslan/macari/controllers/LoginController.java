package ruslan.macari.controllers;

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
import ruslan.macari.models.User;
import ruslan.macari.service.UserService;
import ruslan.macari.validator.UserValidator;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService us) {
        this.userService = us;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpSession session, Model model) {
        model.addAttribute("listUsers", userService.listUsers());
        model.addAttribute("user", new User());
        //new ModelAndView("login", "user", new User());
        //return new ModelAndView("login", "user", new User());
        return "login";
    }
    
    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView createUser(HttpSession session, Model model) {
        return new ModelAndView("createUser", "user", new User());
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public String checkUser(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createUser";
        }
        userService.addUser(user);
        model.addAttribute("listUsers", userService.listUsers());
        return "login";
    }
    
    @RequestMapping(value = "/check-login", method = RequestMethod.POST)
    public String checkLogin(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }
        model.addAttribute("user", user);
        model.addAttribute("listUsers", userService.listUsers());
        return "main";
    }

   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
       // Form target
       Object target = dataBinder.getTarget();
       if (target == null) {
           return;
       }
       System.out.println("Target=" + target);
 
       if (target.getClass() == User.class) {
           dataBinder.setValidator(userValidator);
       }
   }
    
}
