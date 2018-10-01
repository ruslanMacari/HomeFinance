package ruslan.macari.validator;
 
import ruslan.macari.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ruslan.macari.models.UserLogin;
 
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
        User userById = userDAO.getUserById(user.getId());
        if(!userById.getPassword().equals(user.getPassword())) {
            errors.rejectValue("password", "Incorect.user.password");
        }
    }
}