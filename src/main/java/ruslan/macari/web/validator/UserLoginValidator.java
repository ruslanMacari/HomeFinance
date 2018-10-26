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
        User foundUser = userService.getByNameAndPassword(user.getName(), user.getPassword());
        if(foundUser == null) {
            errors.rejectValue("password", "authorization.error");
        }
    }
}