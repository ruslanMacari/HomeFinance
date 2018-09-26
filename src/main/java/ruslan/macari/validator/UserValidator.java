package ruslan.macari.validator;
 
//import org.apache.commons.validator.routines.EmailValidator;
import ruslan.macari.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
@Component
public class UserValidator implements Validator {
    
    private User user;
    
    // The classes is supported to Validate
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        setUser(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.user.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.user.password");
        validateNameLength(errors);
        validatePasswordLength(errors);
    }

    private void setUser(Object target) {
        user = (User) target;
    }

    private void validateNameLength(Errors errors) {
       if(errors.getFieldErrorCount("name") > 0) {
           return;
       }
       int nameLen = user.getName().length();
       if (nameLen < 3) {
            errors.rejectValue("name", "MinSize.user.name");
        }
    }

    private void validatePasswordLength(Errors errors) {
        if (errors.getFieldErrorCount("password") > 0) {
            return;
        }
        int passLen = user.getPassword().length();
        if (passLen < 4) {
            errors.rejectValue("password", "MinSize.user.password");
        }
    }
 
}