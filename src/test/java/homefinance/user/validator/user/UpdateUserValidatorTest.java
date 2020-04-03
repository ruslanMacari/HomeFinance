package homefinance.user.validator.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import homefinance.user.validator.UpdateUserValidator;
import org.junit.Test;
import org.springframework.validation.Errors;

public class UpdateUserValidatorTest {

  @Test
  public void testValidate() {
    UpdateUserValidator validator = new UpdateUserValidator();
    UserService userService = mock(UserService.class);
    validator.setUserService(userService);
    User user = mock(User.class);
    Errors errors = mock(Errors.class);
    when(user.getName()).thenReturn("na");
    when(user.getPassword()).thenReturn("pa");
    User userFound = mock(User.class);
    when(userFound.getId()).thenReturn(555);
    when(userService.getByName(user.getName())).thenReturn(userFound);
    validator.validate(user, errors);
    verify(errors, times(1)).rejectValue("name", "Duplicated.user.name");
  }

}
