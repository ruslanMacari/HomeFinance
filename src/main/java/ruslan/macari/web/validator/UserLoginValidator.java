package ruslan.macari.web.validator;
 
import ruslan.macari.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ruslan.macari.domain.UserLogin;
 
@Component
public class UserLoginValidator extends UserValidator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserLogin.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        setUser(target);
        validateLogin(errors);
    }

    private void setUser(Object target) {
        user = (UserLogin) target;
    }
    
    private void validateLogin(Errors errors) {
        User userById = userService.getById(user.getId());
        if(!userById.getPassword().equals(user.getPassword())) {
            errors.rejectValue("password", "Incorect.user.password");
        }
    }
}