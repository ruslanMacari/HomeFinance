/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruslan.macari.validator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.Errors;
import ruslan.macari.domain.User;

/**
 *
 * @author User
 */
public class UserValidatorIT {
    
    public UserValidatorIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testSupports() {
        System.out.println("supports");
        Class clazz = User.class;
        UserValidator instance = new UserValidator();
        boolean expResult = true;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidate() {
//        System.out.println("validate");
//        Object target = null;
//        Errors errors = new AbstractBindingResult("") {
//            @Override
//            public Object getTarget() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//            
//            @Override
//            protected Object getActualFieldValue(String field) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };
//        UserValidator instance = new UserValidator();
//        instance.validate(target, errors);
//        fail("The test case is a prototype.");
    }
    
}
