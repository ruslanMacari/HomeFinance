package homefinance.web;

import java.security.Principal;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.springframework.ui.Model;

public class MainControllerTest {
    
    private MainController mainController = new MainController();
    
    @Test
    public void testIndex() {
        assertTrue(mainController.index().equals("index"));
    }
    
    @Test
    public void testAccesssDenied() {
        String result = mainController.accesssDenied(mock(Principal.class), mock(Model.class));
        assertTrue(result.equals("access-denied"));
    }
}
