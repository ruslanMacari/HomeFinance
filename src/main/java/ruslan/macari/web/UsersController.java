package ruslan.macari.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ruslan.macari.security.Role;
import ruslan.macari.security.User;
import ruslan.macari.service.UserService;
import ruslan.macari.web.exceptions.DuplicateFieldsException;
import ruslan.macari.web.exceptions.PageNotFoundException;

@Controller
@RequestMapping(UsersController.URL)
public class UsersController {

    private UserService userService;
    private PasswordEncoder encoder;
    private String rootname;
    static final String URL = "/users";
    static final String NEW = "/new";
    static final String NEW_PATH = URL + NEW;
    static final String REDIRECT_PATH = "redirect:" + URL;
    static final String LIST_PATH = URL + "/list";
    static final String VIEW_PATH = URL + "/view";
    
    @Value("${db.username}")
    public void setRootname(String rootname) {
        this.rootname = rootname;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier("passwordEncoder")
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping()
    public String list(Model model) {
        List<User> users = userService.usersExceptRoot();
        model.addAttribute("users", users);
        return LIST_PATH;
    }
    
    @GetMapping(value = "/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        User user = userService.getById(id);
        testRoot(user);
        model.addAttribute("user", user);
        return VIEW_PATH;
    }
    
    private void testRoot(User user) {
        if (user == null
                || user.getName().equals(rootname)) {
            throw new PageNotFoundException();
        }
    }
    
    @PostMapping(value = "/{id}")
    public String update(@Valid @ModelAttribute("user") User user, BindingResult result,
            @PathVariable("id") Integer id, @RequestParam(value = "changePassword", defaultValue = "false") boolean changePassword,
            @RequestParam(value = "admin", defaultValue = "false") boolean admin) {
        if (result.hasErrors()) {
            return VIEW_PATH;
        }
        User foundUser = userService.getById(id);
        foundUser.setName(user.getName());
        if (changePassword) {
            foundUser.setPassword(encoder.encode(user.getPassword()));
        }
        foundUser.setOneRole(admin ? Role.ADMIN : Role.USER);
        userService.update(foundUser);
        return REDIRECT_PATH;
    }
    
    @GetMapping(value = NEW)
    public String newUser(Model model) {
        model.addAttribute("newUser", new User());
        return NEW_PATH;
    }
    
    @PostMapping(value = "/new")
    public String save(@Valid @ModelAttribute("newUser") User user, BindingResult result,
            @RequestParam(value = "admin", defaultValue = "false") boolean admin) {
        if (result.hasErrors()) {
            return NEW_PATH;
        }
        try {
            add(user, admin);
            return REDIRECT_PATH;
        } catch (DuplicateFieldsException ex) {
            result.rejectValue(ex.getField(), ex.getErrorCode());
            return NEW_PATH;
        }
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
        return REDIRECT_PATH;
    }

}
