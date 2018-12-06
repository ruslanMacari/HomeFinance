package ruslan.macari.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.security.Role;
import ruslan.macari.security.User;
import ruslan.macari.service.UserService;
import ruslan.macari.web.exceptions.PageNotFoundException;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;
    private PasswordEncoder encoder;
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    @Qualifier("passwordEncoder")
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
    
    @Autowired
    @Qualifier("updateUserValidator")
    private Validator updateUserValidator;
    
    @Autowired
    @Qualifier("newUserValidator")
    private Validator newUserValidator;
    
    @GetMapping()
    public String list(Model model) {
        List<User> users = userService.usersExceptRoot();
        model.addAttribute("users", users);
        return "users/list";
    }
    
    @GetMapping(value = "/{id}")
    public String view(@PathVariable("id") Integer id, Model model) throws PageNotFoundException {
        User user = userService.getById(id);
        if (user == null) {
            throw new PageNotFoundException ();
        }
        model.addAttribute("user", user);
        return "users/view";
    }
    
    @ExceptionHandler(PageNotFoundException.class)
    public ModelAndView pageNotFoundException() {
        return new ModelAndView("resource-not-found");
    }
    
    @PostMapping(value = "/{id}")
    public String update(@Valid @ModelAttribute("user") User user, BindingResult result,
            @PathVariable("id") Integer id, @RequestParam(value = "changePassword", defaultValue = "false") boolean changePassword,
            @RequestParam(value = "admin", defaultValue = "false") boolean admin ) {
        if (result.hasErrors()) {
            return "users/view";
        }
        User foundUser = userService.getById(id);
        foundUser.setName(user.getName());
        if (changePassword) {
            foundUser.setPassword(encoder.encode(user.getPassword()));
        }
        foundUser.setOneRole(admin ? Role.ADMIN : Role.USER);
        userService.update(foundUser);
        return "redirect:/users";
    }
    
    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("newUser", new User());
        return "users/new";
    }
    
    @PostMapping(value = "/new")
    public String save(@Valid @ModelAttribute("newUser") User user, BindingResult result, 
            @RequestParam(value = "admin", defaultValue = "false") boolean admin) {
        if (result.hasErrors()) {
            return "users/new";
        }
        add(user, admin);
        return "redirect:/users";
    }
    
    private void add(User user, boolean admin) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setOneRole(admin ? Role.ADMIN : Role.USER);
        userService.add(user);
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
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
