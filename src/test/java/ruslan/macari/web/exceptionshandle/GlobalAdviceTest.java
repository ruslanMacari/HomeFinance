package ruslan.macari.web.exceptionshandle;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;

public class GlobalAdviceTest {
    
    private GlobalAdvice advice;
    private Exception e;
    
    @Before
    public void init() {
        advice = new GlobalAdvice();
        e = mock(Exception.class);
    }
    
    @Test
    public void testException() {
        assertEquals(advice.exception(e), "exception");
    }

    @Test
    public void testNoHandlerFound() {
        assertEquals(advice.noHandlerFound(e), "resource-not-found");
    }
    
}
