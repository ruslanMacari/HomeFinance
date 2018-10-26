package ruslan.macari.web.validator;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

public class UserValidatorTest {
    
    private UserValidator userValidator;
    private User user;
    private Errors errors;
    private UserService userService;
            
    public UserValidatorTest() {
        init();
    }
    
    private void init() {
        userValidator = new UserValidator();
        userService = mock(UserService.class);
        userValidator.setUserService(userService);
    }
    
    @Test
    public void testSupports() {
        assertTrue(userValidator.supports(User.class));
        assertFalse(userValidator.supports(Object.class));
    }
    
    @Test
    public void testValidate() {
        namePasswordEmpty();
        passwordEmpty();
        namePasswordMinSize();
        nameDuplication();
    }
    
    private void namePasswordEmpty() {
        user = new User();
        user.setName("");
        user.setPassword("");
        errors = new BeanPropertyBindingResult(user, "User");
        userValidator.validate(user, errors);
        assertTrue(errors.getErrorCount()==2);
        assertTrue(errors.getFieldError("name").getCode().equals("NotEmpty.user.name"));
        assertTrue(errors.getFieldError("password").getCode().equals("NotEmpty.user.password"));
    }

    private void passwordEmpty() {
        user.setName("name");
        errors = new BeanPropertyBindingResult(user, "User");
        userValidator.validate(user, errors);
        assertTrue(errors.getErrorCount()==1);
    }

    private void namePasswordMinSize() {
        user.setName("na");
        user.setPassword("pas");
        errors = new BeanPropertyBindingResult(user, "User");
        userValidator.validate(user, errors);
        assertTrue(errors.getErrorCount()==2);
        assertTrue(errors.getFieldError("name").getCode().equals("MinSize.user.name"));
        assertTrue(errors.getFieldError("password").getCode().equals("MinSize.user.password"));
    }

    private void nameDuplication() {
        user.setName("name");
        user.setPassword("pass");
        errors = new BeanPropertyBindingResult(user, "User");
        when(userService.getByName(user.getName())).thenReturn(user);
        userValidator.validate(user, errors);
        assertTrue(errors.getErrorCount()==1);
        assertTrue(errors.getFieldError("name").getCode().equals("Duplicated.user.name"));
    }
    
}
