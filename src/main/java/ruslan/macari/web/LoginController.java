package ruslan.macari.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserService userService;
    private User root;
    private CurrentUser currentUser;
    private PasswordEncoder encoder;
    
    @Autowired
    @Qualifier("newUserValidator")
    private Validator newUserValidator;

    @Autowired
    @Qualifier("userLoginValidator")
    private Validator userLoginValidator;
    
    @Autowired
    public void setRoot(User root) {
        this.root = root;
    } 
    
    @Autowired
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
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
        if (userService.getAdmin() != null) return;
        userService.add(root);
    }

    @GetMapping()
    public String login(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
//        Object flashModel = model.asMap().get("model");
//        if (flashModel == null) {
            model.addAttribute("listUsers", userService.getSimpleUsers());
            model.addAttribute("userLogin", new User());
//        } else {
//            model.mergeAttributes(((Model) flashModel).asMap());
//        }
        return "auth/login";
    }
    
    @GetMapping("/registration")
    public String registration(Model model) {
        Object flashModel = model.asMap().get("model");
        if (flashModel == null) {
            model.addAttribute("user", new User());
        } else {
            model.mergeAttributes(((Model) flashModel).asMap());
        }
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("model", model);
            return "redirect:/login/registration";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        userService.add(user);
        return "redirect:/login";
    }

    @InitBinder("userLogin")
    protected void initUserLoginBinder(WebDataBinder dataBinder) {
        setValidator(dataBinder, userLoginValidator);
    }
    
    private void setValidator(WebDataBinder dataBinder, Validator validator) {
        if (dataBinder.getTarget() == null) {
            return;
        }
        dataBinder.setValidator(validator);
    }
    
    @InitBinder("user")
    protected void initUserBinder(WebDataBinder dataBinder) {
        setValidator(dataBinder, newUserValidator);
    }

}
