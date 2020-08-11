package homefinance.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTest {

  private UserFacade userFacade;
  @Mock
  private UserService userServiceMock;
  @Mock
  private User userMock;
  @Mock
  private AuthenticationService authenticationServiceMock;
  @Mock
  private UserMapper userMapperMock;

  @Before
  public void setUp() {
    userFacade = new UserFacade(userServiceMock, authenticationServiceMock, userMapperMock);
    given(authenticationServiceMock.getPrincipalName()).willReturn(Optional.empty());
  }

  @Test
  public void getUsersWithoutRoot_givenUserList_returnUsersDto() {
    // given:
    setUserMock("B1646sgb", 116, "3SY", true);
    List<User> users = Arrays.asList(userMock);
    given(userServiceMock.usersExceptRoot()).willReturn(users);
    // when:
    List<UserDto> actual = userFacade.getUsersWithoutRoot();
    // then:
    then(actual.size()).isEqualTo(1);
    UserDto userDto = actual.get(0);
    then(userDto.getId()).isEqualTo(116);
    then(userDto.getName()).isEqualTo("B1646sgb");
    then(userDto.getPassword()).isEqualTo("3SY");
    then(userDto.isAdmin()).isTrue();
  }

  private void setUserMock(String name, int id, String pass, boolean isAdmin) {
    given(userMock.getName()).willReturn(name);
    given(userMock.getId()).willReturn(id);
    given(userMock.getPassword()).willReturn(pass);
    given(userMock.hasAdmin()).willReturn(isAdmin);
  }

  @Test
  public void getUserById_givenUserServiceGetByIdReturnsUser_returnUserDto() {
    //given:
    setUserMock("name", 56, "pass", false);
    given(userServiceMock.getById(55)).willReturn(userMock);
    //when:
    UserDto actual = userFacade.getUserById(55);
    //then:
    then(actual.getId()).isEqualTo(56);
    then(actual.getName()).isEqualTo("name");
    then(actual.getPassword()).isEqualTo("pass");
    then(actual.isAdmin()).isFalse();
  }

  @Test
  public void getUserById_givenUserServiceGetByIdReturnsNull_returnNull() {
    //given:
    given(userServiceMock.getById(55)).willReturn(null);
    //when:
    UserDto actual = userFacade.getUserById(55);
    //then:
    then(actual).isNull();
  }

  @Test
  public void deleteUser() {
    //when:
    userFacade.deleteUser(15);
    //then:
    BDDMockito.then(userServiceMock).should().delete(15);
  }

  @Test
  public void getUsersWithoutRoot_givenUsersAndPrincipal_returnUsersWithUserLoggedIn() {
    //given:
    given(userMock.getName()).willReturn("user 1");
    User userMockLoggedIn = mock(User.class);
    given(userMockLoggedIn.getName()).willReturn("user logged in");
    List<User> users = Arrays.asList(userMock, userMockLoggedIn);
    given(userServiceMock.usersExceptRoot()).willReturn(users);
    given(authenticationServiceMock.getPrincipalName()).willReturn(Optional.of("user logged in"));
    //when:
    List<UserDto> actual = userFacade.getUsersWithoutRoot();
    //then:
    then(actual.get(0).isLoggedIn()).isFalse();
    then(actual.get(1).isLoggedIn()).isTrue();
  }

  @Test
  public void addUser_givenUserDto_addUser() {
    //given:
    UserDto userDtoMock = mock(UserDto.class);
    given(userMapperMock.dtoToUser(userDtoMock)).willReturn(userMock);
    //when:
    userFacade.addUser(userDtoMock);
    //then:
    BDDMockito.then(userServiceMock).should().add(userMock);
  }
}