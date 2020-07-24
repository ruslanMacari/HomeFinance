package homefinance.user.login;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.user.AuthenticationFacade;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

@RunWith(MockitoJUnitRunner.class)
public class LoginFacadeTest {

  private LoginFacade loginFacade;
  @Mock
  private Authentication authMock;
  @Mock
  private UserService userServiceMock;
  @Mock
  private AuthenticationFacade authenticationFacadeMock;

  @Before
  public void setUp() {
    loginFacade = new LoginFacade(userServiceMock, authenticationFacadeMock);
  }

  @Test
  public void isAuthenticated_givenAuthenticationIsAuthenticatedIsTrue_thenReturnTrue() {
    // given:
    given(authenticationFacadeMock.getAuthentication()).willReturn(Optional.of(authMock));
    given(authMock.isAuthenticated()).willReturn(true);
    // when:
    boolean actual = loginFacade.isAuthenticated();
    // then:
    then(actual).isTrue();
  }

  @Test
  public void isAuthenticated_givenAuthenticationIsAuthenticatedIsFalse_thenReturnFalse() {
    // given:
    given(authenticationFacadeMock.getAuthentication()).willReturn(Optional.of(authMock));
    given(authMock.isAuthenticated()).willReturn(false);
    // when:
    boolean actual = loginFacade.isAuthenticated();
    // then:
    then(actual).isFalse();
  }

  @Test
  public void isAuthenticated_givenAuthenticationIsAuthenticatedIsTrueAndTypeIsAnonymousAuthenticationToken_thenReturnFalse() {
    // given:
    AnonymousAuthenticationToken anonymousAuthenticationTokenMock = mock(AnonymousAuthenticationToken.class);
    given(authenticationFacadeMock.getAuthentication()).willReturn(Optional.of(anonymousAuthenticationTokenMock));
    given(anonymousAuthenticationTokenMock.isAuthenticated()).willReturn(true);
    // when:
    boolean actual = loginFacade.isAuthenticated();
    // then:
    then(actual).isFalse();
  }

  @Test
  public void getSimpleUsersNames_givenUser_shouldReturnUserNameList() {
    // given:
    User user = mock(User.class);
    given(user.getName()).willReturn("user1");
    List<User> simpleUsers = Collections.singletonList(user);
    given(userServiceMock.getSimpleUsers()).willReturn(simpleUsers);

    // when:
    List<String> actual = loginFacade.getSimpleUsersNames();
    // then:
    then(actual.size()).isEqualTo(1);
    then(actual.get(0)).isEqualTo("user1");
  }

  @Test
  public void registerUser_givenUser_shouldRegisterUserByNameAndPassword() {
    // given:
    UserLoginDto userLoginDto = mock(UserLoginDto.class);
    given(userLoginDto.getName()).willReturn("test name");
    given(userLoginDto.getPassword()).willReturn("test pass");
    // when:
    loginFacade.registerUser(userLoginDto);
    // then:
    BDDMockito.then(userServiceMock).should().registerUser("test name", "test pass");
  }
}