package ruslan.macari.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ruslan.macari.domain.CurrentUser;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping()
    public String showUsers(HttpSession session, Model model) {
        if (!CurrentUser.isAdmin(session.getId())) {
            return "users/list";
        }
        List<User> users = userService.list();
        model.addAttribute("users", users);
        return "users/list";
    }
    
}
