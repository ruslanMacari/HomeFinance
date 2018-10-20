package ruslan.macari.validator;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.validation.Errors;
import ruslan.macari.domain.User;
import ruslan.macari.service.UserService;

public class UserValidatorTest {
    
    private UserValidator userValidator;
            
    public UserValidatorTest() {
        init();
    }
    
    private void init() {
        userValidator = new UserValidator();
        userValidator.setUserService(mock(UserService.class));
    }
    
    @Test
    public void testSupports() {
        assertTrue(userValidator.supports(User.class));
        assertFalse(userValidator.supports(Object.class));
    }
    
    @Test
    public void testValidate() {
        User user = new User();
        user.setName("p");
        user.setPassword("p");
        Errors errors = mock(Errors.class);
        when(errors.getFieldErrorCount("name")).thenReturn(0);
        userValidator.validate(user, errors);
        //verify()
    }
    
}
