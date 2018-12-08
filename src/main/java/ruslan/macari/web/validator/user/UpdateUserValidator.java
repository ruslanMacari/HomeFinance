package ruslan.macari.web.validator.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ruslan.macari.security.User;

@Component
@Qualifier("updateUserValidator")
public class UpdateUserValidator extends UserValidator {

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
        checkDuplication(errors);
        
    }
    
    private void checkDuplication(Errors errors) {
        if (errors.getFieldError("name") == null) {
            User userFound = userService.getByNameExceptID(user.getName(), user.getId());
            if (userFound != null) {
                errors.rejectValue("name", "Duplicated.user.name");
            }
        }
    }
    
}
