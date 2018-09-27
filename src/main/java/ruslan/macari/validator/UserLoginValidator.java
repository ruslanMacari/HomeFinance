package ruslan.macari.validator;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ruslan.macari.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ruslan.macari.models.UserLogin;
import ruslan.macari.service.UserService;
 
@Component
public class UserLoginValidator implements Validator {
    
    private UserLogin user;
    
    private UserService userService;
    
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService us) {
        this.userService = us;
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserLogin.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        setUser(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.user.password");
        validatePasswordLength(errors);
        validateLogin(errors);
    }

    private void setUser(Object target) {
        user = (UserLogin) target;
    }
    
    private void validatePasswordLength(Errors errors) {
        if (errors.getFieldErrorCount("password") == 0
                && user.getPassword().length() < 4) {
            errors.rejectValue("password", "MinSize.user.password");
        }
    }

    private void validateLogin(Errors errors) {
        User userById = userService.getUserById(user.getId());
        if(!userById.getPassword().equals(user.getPassword())) {
            errors.rejectValue("password", "Incorect.user.password");
        }
    }

    
}