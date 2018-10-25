package ruslan.macari.web.validator;

import ruslan.macari.web.validator.UserLoginValidator;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ruslan.macari.domain.UserLogin;
import ruslan.macari.service.UserService;

public class UserLoginValidatorTest {
    
    private UserLoginValidator userLoginValidator;
    private UserService userService;
            
    public UserLoginValidatorTest() {
        init();
    }
    
    private void init() {
        userLoginValidator = new UserLoginValidator();
        userService = mock(UserService.class);
        userLoginValidator.setUserService(userService);
    }
    
    @Test
    public void testSupports() {
        assertTrue(userLoginValidator.supports(UserLogin.class));
        assertFalse(userLoginValidator.supports(Object.class));
    }
    
    @Test
    public void testValidate() {
        UserLogin user = new UserLogin();
        user.setPassword("pass");
        Errors errors = new BeanPropertyBindingResult(user, "User");
        UserLogin ul = new UserLogin();
        ul.setPassword("pass1");
        when(userService.getById(user.getId())).thenReturn(ul);
        userLoginValidator.validate(user, errors);
        assertTrue(errors.getErrorCount()==1);
        assertTrue(errors.getFieldError("password").getCode().equals("Incorect.user.password"));
    }
    
}