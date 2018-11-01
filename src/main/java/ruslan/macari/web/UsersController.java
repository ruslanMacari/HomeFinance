package ruslan.macari.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;
import ruslan.macari.web.exceptions.AccesException;
import ruslan.macari.web.validator.UserValidator;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    private UserValidator userValidator;
    
    @GetMapping()
    public String showUsers(HttpSession session, Model model) throws AccesException {
        handleAccess(session);
        List<User> users = userService.list();
        model.addAttribute("users", users);
        return "users/list";
    }
    
    private void handleAccess(HttpSession session) throws AccesException {
        String id = session.getId();
        if (!CurrentUser.isAdmin(id)) {
            throw new AccesException(id);
        }
    }
    
    @ExceptionHandler(AccesException.class)
    public ModelAndView handleDeleteException(AccesException e) {
        ModelMap model = new ModelMap();
        model.put("user", e.getUser());
        return new ModelAndView("users/access-denied", model);
    }
    
    @GetMapping(value = "/{id}")
    public String getEmployee(HttpSession session, @PathVariable("id") int id, Model model) throws AccesException {
        handleAccess(session);
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "users/view";
    }
    
    @PostMapping(value = "/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result, 
            Model model, @PathVariable("id") int id) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "users/view";
        }
        user.setId(id);
        userService.update(user);
        return "redirect:/users";
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        if (dataBinder.getTarget() == null) {
            return;
        }
        dataBinder.setValidator(userValidator);
    }

}
