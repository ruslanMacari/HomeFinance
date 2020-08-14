package homefinance.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import homefinance.user.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

  private UserMapper userMapper;
  @Mock
  private UserDto userDtoMock;
  @Mock
  private PasswordEncoder encoderMock;

  @Before
  public void setUp() {
    userMapper = new UserMapper(encoderMock);
  }

  @Test
  public void dtoToUser_givenDtoAdmin_returnUserAdmin() {
    //given:
    given(userDtoMock.getId()).willReturn(10);
    given(userDtoMock.getName()).willReturn("user");
    given(userDtoMock.isAdmin()).willReturn(true);
    //when:
    User actual = userMapper.dtoToUser(userDtoMock);
    //then:
    then(actual.getId()).isEqualTo(10);
    then(actual.getName()).isEqualTo("user");
    then(actual.isEnabled()).isTrue();
    then(actual.getUserRole().size()).isEqualTo(1);
    then(actual.isAdmin()).isTrue();
  }

  @Test
  public void dtoToUser_givenDtoNotAdmin_returnSimpleUser() {
    //given:
    given(userDtoMock.isAdmin()).willReturn(false);
    //when:
    User actual = userMapper.dtoToUser(userDtoMock);
    //then:
    then(actual.getUserRole().size()).isEqualTo(1);
    then(actual.isAdmin()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void dtoToUser_givenDtoIsNull_shouldThrowIllegalArgumentException() {
    //when:
    userMapper.dtoToUser(null);
  }

  @Test
  public void dtoToUser_givenDtoPassword_returnUserWithEncodedPassword() {
    //given:
    given(userDtoMock.getPassword()).willReturn("pass");
    given(encoderMock.encode("pass")).willReturn("#pass encoded#");
    //when:
    User actual = userMapper.dtoToUser(userDtoMock);
    //then:
    then(actual.getPassword()).isEqualTo("#pass encoded#");
  }
}