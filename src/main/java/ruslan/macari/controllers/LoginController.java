package ruslan.macari.controllers;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.models.User;
import ruslan.macari.service.UserService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(HttpSession session, Model model) {
        model.addAttribute("listUsers", this.userService.listUsers());
        return new ModelAndView("login", "user", new User());
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ModelAndView checkUser(@ModelAttribute("user") User user, Model model) {
        return new ModelAndView("main", "user", user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView failed(ModelAndView modelAndView) {
        return modelAndView;
    }

}
