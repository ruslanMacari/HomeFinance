package ruslan.macari.web.validator.user;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.validation.Errors;
import ruslan.macari.security.User;
import ruslan.macari.service.UserService;

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
