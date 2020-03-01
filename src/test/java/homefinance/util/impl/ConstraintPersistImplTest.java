package homefinance.util.impl;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.springframework.dao.DataIntegrityViolationException;
import homefinance.entity.ConstraintEntity;
import homefinance.web.exceptions.DuplicateFieldsException;

public class ConstraintPersistImplTest {
    
    private ConstraintPersistImpl instance = new ConstraintPersistImpl();
    private ConstraintEntity entity = mock(ConstraintEntity.class);
    private String errorMsg = "exception";
    private String msg = "Test Error";
    private DataIntegrityViolationException exeption = new DataIntegrityViolationException(msg, new Exception(errorMsg));
    
    @Test
    public void testAdd() {
        Map<String, String> map = new HashMap<>();
        map.put(errorMsg, errorMsg);
        ConstraintEntity result = instance.add(() -> getEntityOk(), map);
        assertEquals(result, entity);
        try {
            instance.add(() -> getEntityError(), map);
            fail("DuplicateFieldsException must be thrown");
        } catch (DuplicateFieldsException duplicateEx) {
             assertTrue(duplicateEx.getErrorCode().equals(errorMsg));
            try {
                map.clear();
                instance.add(() -> getEntityError(), map);
                fail("DataIntegrityViolationException must be thrown");
            } catch (DataIntegrityViolationException e) {
                assertTrue(e.getMessage().contains(msg));
            }

        }
    }
    
    private ConstraintEntity getEntityOk() {
        return entity;
    }
    
    private ConstraintEntity getEntityError() {
        if (true) throw new DataIntegrityViolationException("Test Error", new Exception(errorMsg));
        return entity;
    }

    @Test
    public void testUpdate() {
        testAdd();
    }
    
}
