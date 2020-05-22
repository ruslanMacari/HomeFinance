package homefinance.user.login;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class LoginFacadeTest {

  private LoginFacade loginFacade;
  @Mock
  private Authentication authMock;

  @Before
  public void setUp() {
    this.loginFacade = new LoginFacade();
  }

  @Test
  public void isAuthenticated_givenAuthenticationIsAuthenticatedIsTrue_thenReturnTrue() {
    // given:
    this.mockAuthContext();
    given(this.authMock.isAuthenticated()).willReturn(true);
    // when:
    boolean actual = this.loginFacade.isAuthenticated();
    // then:
    then(actual).isTrue();
  }

  private void mockAuthContext() {
    SecurityContext context = this.getSecurityContext();
    given(context.getAuthentication()).willReturn(this.authMock);
  }

  private SecurityContext getSecurityContext() {
    SecurityContext context = mock(SecurityContext.class);
    SecurityContextHolder.setContext(context);
    return context;
  }

  @Test
  public void isAuthenticated_givenAuthenticationIsAuthenticatedIsFalse_thenReturnFalse() {
    // given:
    this.mockAuthContext();
    given(this.authMock.isAuthenticated()).willReturn(false);
    // when:
    boolean actual = this.loginFacade.isAuthenticated();
    // then:
    then(actual).isFalse();
  }

  @Test
  public void isAuthenticated_givenAuthenticationIsAuthenticatedIsTrueAndTypeIsAnonymousAuthenticationToken_thenReturnFalse() {
    // given:
    SecurityContext context = this.getSecurityContext();
    AnonymousAuthenticationToken anonymousAuthenticationTokenMock = mock(
        AnonymousAuthenticationToken.class);
    given(context.getAuthentication()).willReturn(anonymousAuthenticationTokenMock);
    given(anonymousAuthenticationTokenMock.isAuthenticated()).willReturn(true);
    // when:
    boolean actual = this.loginFacade.isAuthenticated();
    // then:
    then(actual).isFalse();
  }
}