package homefinance.home;

import static homefinance.home.HomeController.USERNAME_ATTRIBUTE;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.ui.Model;

public class HomeControllerTest {

  public static final String USERNAME = "user";
  private HomeController controller;

  @BeforeEach
  public void setUp() {
    controller = new HomeController();
  }

  @Test
  public void home_shouldReturnHomeViewName() {
    // when:
    String viewName = controller.home();
    // then:
    then(viewName).isEqualTo("home");
  }

  @Test
  public void accessDenied_givenUser_shouldAddUserToModelAndReturnAccessDeniedViewName() {
    // given:
    Principal principalMock = mock(Principal.class);
    given(principalMock.getName()).willReturn(USERNAME);
    Model model = mock(Model.class);
    // when:
    String viewName = controller.accessDenied(principalMock, model);
    // then:
    then(viewName).isEqualTo("access-denied");
    BDDMockito.then(model).should().addAttribute(USERNAME_ATTRIBUTE, USERNAME);
  }
}
