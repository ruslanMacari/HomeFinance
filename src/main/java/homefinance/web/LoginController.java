package homefinance.web;

import homefinance.web.exceptions.DuplicateFieldsException;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import homefinance.security.Role;
import homefinance.security.User;
import homefinance.service.UserService;
import homefinance.util.PathSelector;

@Controller
@RequestMapping(LoginController.URL)
public class LoginController {

    public static final String URL = "/login";
    public static final String REDIRECT = "redirect:";
    public static final String REDIRECT_ROOT = REDIRECT + "/";
    public static final String REDIRECT_URL = REDIRECT + URL;
    public static final String DIRECTORY = "/auth";
    public static final String URL_PATH = DIRECTORY + URL;
    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_PATH = DIRECTORY + REGISTRATION;
    public static final String REDIRECT_REGISTRATION = REDIRECT + URL + REGISTRATION;
            
    private UserService userService;
    private String rootpassword;
    private String rootname;
    private PasswordEncoder encoder;
    private PathSelector pathSelector;

    @Autowired
    public void setPathSelector(PathSelector pathSelector) {
        this.pathSelector = pathSelector;
    }
    
    @Value("${db.password}")
    public void setRootpassword(String rootpassword) {
        this.rootpassword = rootpassword;
    }
    
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

    @PostConstruct
    public void init() {
        if (userService.getByName(rootname) != null) return;
        User root = new User();
        root.setName(rootname);
        root.setPassword(encoder.encode(rootpassword));
        root.setOneRole(Role.ADMIN);
        userService.add(root);
    }

    @GetMapping()
    public String login(Model model) {
        if (isAuthenticated()) {
            return REDIRECT_ROOT;
        }
        model.addAttribute("listUsers", userService.getSimpleUsers());
        model.addAttribute("user", new User());
        return URL_PATH;
    }
    
    private boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken);
    }
    
    @GetMapping(REGISTRATION)
    public String registration(Model model) {
        Object flashModel = model.asMap().get("model");
        if (flashModel == null) {
            model.addAttribute("user", new User());
        } else {
            model.mergeAttributes(((Model) flashModel).asMap());
        }
        return REGISTRATION_PATH;
    }

    @PostMapping(REGISTRATION)
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, 
            RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("model", model);
            return REDIRECT_REGISTRATION;
        }
        pathSelector.setActionOk(() -> addUser(user));
        pathSelector.setActionError(() -> redirectAttributes.addFlashAttribute("model", model));
        return pathSelector.setPaths(REDIRECT_URL, REDIRECT_REGISTRATION).setErrors(result).getPath();
    }
    
    private void addUser(User user) throws DuplicateFieldsException {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setOneRole(Role.USER);
        userService.add(user);
    }

}
