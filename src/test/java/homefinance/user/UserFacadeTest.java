package homefinance.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)// TODO: 021, 21-Nov-22 ruslan.macari: fix tests
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
  @Mock
  private UserDto userDtoMock; //TODO: remove mock
  UserDto userDto = new UserDto();

  @BeforeEach
  public void setUp() {
    userFacade = new UserFacade(userServiceMock, authenticationServiceMock, userMapperMock);
    given(authenticationServiceMock.getPrincipalName()).willReturn(Optional.empty());
  }

  @Test
  public void getUsersWithoutRoot_givenUserList_returnUsersDto() {
    // given:
    setUserMock("B1646sgb", 116L, "3SY", true);
    List<User> users = List.of(userMock);
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

  private void setUserMock(String name, Long id, String pass, boolean isAdmin) {
    given(userMock.getName()).willReturn(name);
    given(userMock.getId()).willReturn(id);
    given(userMock.getPassword()).willReturn(pass);
    given(userMock.isAdmin()).willReturn(isAdmin);
  }

  @Test
  public void getUserById_givenUserServiceGetByIdReturnsUser_returnUserDto() {
    //given:
    setUserMock("name", 56L, "pass", false);
    given(userServiceMock.getById(55L)).willReturn(userMock);
    //when:
    UserDto actual = userFacade.getUserById(55L);
    //then:
    then(actual.getId()).isEqualTo(56);
    then(actual.getName()).isEqualTo("name");
    then(actual.getPassword()).isEqualTo("pass");
    then(actual.isAdmin()).isFalse();
  }

  @Test
  public void getUserById_givenUserServiceGetByIdReturnsNull_returnNull() {
    //given:
    given(userServiceMock.getById(55L)).willReturn(null);
    //when:
    UserDto actual = userFacade.getUserById(55L);
    //then:
    then(actual).isNull();
  }

  @Test
  public void deleteUser() {
    //when:
    userFacade.deleteUser(15L);
    //then:
    BDDMockito.then(userServiceMock).should().delete(15L);
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
    given(userMapperMock.dtoToUser(userDtoMock)).willReturn(userMock);
    //when:
    userFacade.addUser(userDtoMock);
    //then:
    BDDMockito.then(userServiceMock).should().add(userMock);
  }

  @Test
  public void update_givenUserDtoIsPasswordChanged_shouldUpdateUser() {
    //given:
    given(userDtoMock.isPasswordChanged()).willReturn(true);
    //when:
    userFacade.update(userDtoMock);
    //then:
    BDDMockito.then(userServiceMock).should().update(userDtoMock);
  }

  @Test
  public void update_givenUserDtoIsNotPasswordChanged_shouldUpdateWithoutPassword() {
    //given:
    given(userDtoMock.isPasswordChanged()).willReturn(false);
    //when:
    userFacade.update(userDtoMock);
    //then:
    BDDMockito.then(userServiceMock).should().updateWithoutPassword(userDtoMock);
  }

  @Test
  void isRoot_givenUsernameIsRoot_shouldReturnTrue() {
    //given:
    userDto.setName("root");
    //when:
    boolean actual = userFacade.isRoot(userDto);
    //then:
    then(actual).isTrue();
  }

  @Test
  void isRoot_givenUsernameIsNotRoot_shouldReturnFalse() {
    //given:
    userDto.setName("user abc");
    //when:
    boolean actual = userFacade.isRoot(userDto);
    //then:
    then(actual).isFalse();
  }

  @Test
  void isRoot_givenUsernameIsNull_shouldReturnFalse() {
    //given:
    userDto.setName(null);
    //when:
    boolean actual = userFacade.isRoot(userDto);
    //then:
    then(actual).isFalse();
  }

  @Test
  void isRoot_givenUserIsNull_shouldReturnFalse() {
    assertThrows(IllegalArgumentException.class, () -> userFacade.isRoot(null));  }
}
