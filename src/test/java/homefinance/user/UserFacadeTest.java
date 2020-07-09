package homefinance.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class UserFacadeTest {

  @Test
  public void getUsersWithoutRoot_givenUserList_returnUsersDto() {
    // given:
    UserService userServiceMock = mock(UserService.class);
    User user = mock(User.class);
    given(user.getName()).willReturn("B1646sgb");
    given(user.getId()).willReturn(116);
    given(user.getPassword()).willReturn("3SY");
    given(user.hasAdmin()).willReturn(true);
    List<User> users = Arrays.asList(user);
    given(userServiceMock.usersExceptRoot()).willReturn(users);
    UserFacade userFacade = new UserFacade(userServiceMock);
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
}