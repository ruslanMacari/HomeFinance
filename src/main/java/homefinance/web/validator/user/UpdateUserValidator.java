package homefinance.web.validator.user;

import homefinance.security.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

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
      User userFound = userService.getByName(user.getName());
      if (userFound != null
          && userFound.getId() != user.getId()) {
        errors.rejectValue("name", "Duplicated.user.name");
      }
    }
  }

}
