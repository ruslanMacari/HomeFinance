package homefinance.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.security.Principal;
import org.junit.Test;
import org.springframework.ui.Model;

public class MainControllerTest {

  private MainController mainController = new MainController();

  @Test
  public void testIndex() {
    assertTrue(mainController.index().equals("index"));
  }

  @Test
  public void testAccesssDenied() {
    String result = mainController.accessDenied(mock(Principal.class), mock(Model.class));
    assertTrue(result.equals("access-denied"));
  }
}
