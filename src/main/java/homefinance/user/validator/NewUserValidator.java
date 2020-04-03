package homefinance.user.validator;

import homefinance.user.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@Qualifier("newUserValidator")
public class NewUserValidator extends UserValidator {

  @Override
  public void validate(Object target, Errors errors) {
    super.validate(target, errors);
    checkDuplication(errors);

  }

  private void checkDuplication(Errors errors) {
    if (errors.getFieldError("name") == null) {
      User userFound = userService.getByName(user.getName());
      if (userFound != null) {
        errors.rejectValue("name", "Duplicated.user.name");
      }
    }
  }

}
