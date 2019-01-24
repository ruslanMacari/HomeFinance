package ruslan.macari.util.impl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.springframework.validation.Errors;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

public class PathSelectorImplTest {
    
    private PathSelectorImpl pathSelector;
    
    @Before
    public void setUp() {
        pathSelector = new PathSelectorImpl();
    }

    @Test
    public void testSetActionOk() {
        assertEquals(pathSelector.setActionOk(() -> System.out.println("test")), pathSelector);
    }

    @Test
    public void testSetActionError() {
        assertEquals(pathSelector.setActionError(() -> System.out.println("test")), pathSelector);
    }

    @Test
    public void testSetPaths() {
        assertEquals(pathSelector.setPaths("test1", "test2"), pathSelector);
    }

    @Test
    public void testSetErrors() {
        assertEquals(pathSelector.setErrors(mock(Errors.class)), pathSelector);
    }

    @Test
    public void testGetPath() {
        String pathOk = "ok";
        String pathError = "error";
        pathSelector.setActionOk(() -> System.out.println("ok")).setPaths(pathOk, pathError);
        assertEquals(pathSelector.getPath(), pathOk);
        pathSelector.setActionOk(() -> {
            throw mock(DuplicateFieldsException.class);
        });
        pathSelector.setErrors(mock(Errors.class)).setActionError(() -> System.out.println("error"));
        assertEquals(pathSelector.getPath(), pathError);
        assertEquals(pathSelector.getPath(), pathError);
    }
    
}
