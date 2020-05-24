package homefinance.user.login;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class LoginFacadeTest {

  private LoginFacade loginFacade;
  @Mock
  private Authentication authMock;
  @Mock
  private UserService userServiceMock;
  @Mock
  private ModelMapper modelMapper;

  @Before
  public void setUp() {
    this.loginFacade = new LoginFacade(this.userServiceMock, this.modelMapper);
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

  @Test
  public void getSimpleUsersNames_givenUser_shouldReturnUserNameList() {
    // given:
    User user = mock(User.class);
    given(user.getName()).willReturn("user1");
    List<User> simpleUsers = Collections.singletonList(user);
    given(this.userServiceMock.getSimpleUsers()).willReturn(simpleUsers);

    // when:
    List<String> actual = this.loginFacade.getSimpleUsersNames();
    // then:
    then(actual.size()).isEqualTo(1);
    then(actual.get(0)).isEqualTo("user1");
  }
}