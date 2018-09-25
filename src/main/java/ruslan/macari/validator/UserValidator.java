package ruslan.macari.validator;
 
//import org.apache.commons.validator.routines.EmailValidator;
import ruslan.macari.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
@Component
public class UserValidator implements Validator {
    
//    // common-validator library.
//    private EmailValidator emailValidator =   EmailValidator.getInstance();
 
    // The classes is supported to Validate
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        User applicantInfo = (User) target;
 
        // Check the fields of User.
        // (See more in property file: messages/validator.property)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.applicantForm.name");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.applicantForm.email");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", "NotEmpty.applicantForm.position");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.applicantForm.gender");
        
//        if(!emailValidator.isValid(applicantInfo.getEmail())) {
//            // Error in email field.
//            errors.rejectValue("email", "Pattern.applicantForm.email");
//        }
//        
//        if(applicantInfo.getSkills()== null || applicantInfo.getSkills().length==0 ) {
//            errors.rejectValue("skills", "Select.applicantForm.skills");
//        }
      
    }
 
}