package ruslan.macari.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ruslan.macari.web.utils.CurrentUser;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;
import ruslan.macari.web.exceptions.AccesException;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping()
    public String showUsers(HttpSession session, Model model) throws AccesException {
        String id = session.getId();
        User user = CurrentUser.get(id);
        if (user == null
                || !user.isAdmin()) {
            throw new AccesException(user);
        }
        List<User> users = userService.list();
        model.addAttribute("users", users);
        return "users/list";
    }
    
    @ExceptionHandler(AccesException.class)
    public ModelAndView handleDeleteException(AccesException e) {
        ModelMap model = new ModelMap();
        model.put("user", e.getUser());
        return new ModelAndView("users/access-denied", model);
    }
    
}
