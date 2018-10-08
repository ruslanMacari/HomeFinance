package ruslan.macari.validator;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ruslan.macari.domain.Currency;

public class CurrencyValidatorTest {
    
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    
    @BeforeClass
    public static void setUpClass() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @AfterClass
    public static void tearDownClass() {
        validatorFactory.close();
    }
    
    @Test
    public void testValidate() {
        //given:
        Currency currency = new Currency();
        currency.setName("M");
        
        //when:
        Set<ConstraintViolation<Currency>> violations = validator.validate(currency);

        //then:
        //assertTrue(violations.isEmpty());
        assertEquals(violations.size(), 1);
        ConstraintViolation<Currency> violation
                = violations.iterator().next();
        assertEquals("Size must be minim 3",
                     violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("M", violation.getInvalidValue());
    }
    
}
