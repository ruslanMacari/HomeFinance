package ruslan.macari.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.security.User;
import ruslan.macari.service.UserService;
import ruslan.macari.web.exceptions.AccessException;
import ruslan.macari.web.exceptions.PageNotFoundException;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    @Qualifier("updateUserValidator")
    private Validator updateUserValidator;
    
    @Autowired
    @Qualifier("newUserValidator")
    private Validator newUserValidator;
    
    @GetMapping()
    public String users(Model model) {
        List<User> users = userService.usersExceptRoot();
        model.addAttribute("users", users);
        return "users/list";
    }
    
    @GetMapping(value = "/{name}")
    public String getUser(HttpSession session, @PathVariable("name") String name, Model model) throws AccessException, PageNotFoundException {
        User user = userService.getByName(name);
        handlePageNotFound(user);
        model.addAttribute("user", user);
        return "users/view";
    }
    
    private void handlePageNotFound(User user) throws PageNotFoundException {
        if (user == null) {
            throw new PageNotFoundException ();
        }
    }
    
    @ExceptionHandler(PageNotFoundException.class)
    public ModelAndView pageNotFoundException(PageNotFoundException e) {
        return new ModelAndView("resource-not-found");
    }
    
    @PostMapping(value = "/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result,
            @PathVariable("id") int id, HttpSession session) throws AccessException {
        //handleAccess(session);
        if (result.hasErrors()) {
            return "users/view";
        }
        //user.setId(id);
        userService.update(user);
        return "redirect:/users";
    }
    
    @GetMapping(params = "new")
    public String createUserForm(HttpSession session, Model model) throws AccessException {
        //handleAccess(session);
        model.addAttribute("newUser", new User());
        return "users/new";
    }
    
    @PostMapping()
    public String addUser(@Valid @ModelAttribute("newUser") User user, BindingResult result, HttpSession session) throws AccessException {
        //handleAccess(session);
        if (result.hasErrors()) {
            return "users/new";
        }
        userService.add(user);
        return "redirect:/users";
    }
    
    @DeleteMapping(value = "/{name}")
    public String deleteUser(HttpSession session, @PathVariable("name") String name) throws AccessException {
        //handleAccess(session);
        userService.delete(name);
        return "redirect:/users";
    }
    
    @InitBinder("user")
    protected void initUserBinder(WebDataBinder dataBinder) {
        setValidator(dataBinder, updateUserValidator);
    }
    
    private void setValidator(WebDataBinder dataBinder, Validator validator) {
        if (dataBinder.getTarget() == null) {
            return;
        }
        dataBinder.setValidator(validator);
    }
    
    @InitBinder("newUser")
    protected void initNewUserBinder(WebDataBinder dataBinder) {
        setValidator(dataBinder, newUserValidator);
    }

}
