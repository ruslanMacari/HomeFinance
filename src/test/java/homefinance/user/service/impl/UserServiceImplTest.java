package homefinance.user.service.impl;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.user.entity.User;
import homefinance.user.service.repository.UserRepository;
import homefinance.common.util.ConstraintPersist;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private UserServiceImpl userService;
  private final String rootname = "root";
  private final String rootpassword = "pass";
  private PasswordEncoder encoder;
  private User user;
  @Mock
  private UserRepository repositoryMock;

  @Before
  public void setUp() {
    userService = new UserServiceImpl();
    user = mock(User.class);
    userService.setRootName(rootname);
    userService.setRootPassword(rootpassword);
    encoder = mock(PasswordEncoder.class);
    userService.setEncoder(encoder);
    userService.setUserRepository(repositoryMock);
  }

  @Test
  public void testInit() {
    ConstraintPersist constraintPersist = mock(ConstraintPersist.class);
    userService.setConstraintPersist(constraintPersist);
    when(repositoryMock.findByName(rootname)).thenReturn(user);
    userService.init();
    verify(constraintPersist, times(0)).add(any(), any());
    when(repositoryMock.findByName(rootname)).thenReturn(null);
    when(encoder.encode(rootpassword)).thenReturn(rootpassword);
    userService.init();
    verify(encoder, times(1)).encode(rootpassword);
    verify(constraintPersist, times(1)).add(any(), any());
  }

  @Test
  public void usersExceptRoot_givenRepositoryReturnsNull_returnEmptyList() {
    // given:
    given(repositoryMock.usersExceptRoot(rootname)).willReturn(null);
    // when:
    List<User> actual = userService.usersExceptRoot();
    // then:
    then(actual.isEmpty()).isTrue();
  }
}