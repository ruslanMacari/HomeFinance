package homefinance.web.validator.user;

import homefinance.security.User;
import homefinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  protected User user;

  protected UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

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
    if (errors.getFieldErrorCount("name") == 0
        && user.getName().length() < 3) {
      errors.rejectValue("name", "MinSize.user.name");
    }
  }

  private void validatePasswordLength(Errors errors) {
    if (errors.getFieldErrorCount("password") == 0
        && user.getPassword().length() < 4) {
      errors.rejectValue("password", "MinSize.user.password");
    }
  }

}