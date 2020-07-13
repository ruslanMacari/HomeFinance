package homefinance.user.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.user.entity.User;
import homefinance.user.service.repository.UserRepository;
import homefinance.common.util.ConstraintPersist;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImplTest {

  private UserServiceImpl userService;
  private final String rootname = "root";
  private final String rootpassword = "pass";
  private PasswordEncoder encoder;
  private User user;

  @Before
  public void setUp() {
    userService = new UserServiceImpl();
    user = mock(User.class);
    userService.setRootName(rootname);
    userService.setRootPassword(rootpassword);
    encoder = mock(PasswordEncoder.class);
    userService.setEncoder(encoder);
  }

  @Test
  public void testInit() {
    ConstraintPersist constraintPersist = mock(ConstraintPersist.class);
    userService.setConstraintPersist(constraintPersist);
    UserRepository repository = mock(UserRepository.class);
    userService.setUserRepository(repository);
    when(repository.findByName(rootname)).thenReturn(user);
    userService.init();
    verify(constraintPersist, times(0)).add(any(), any());
    when(repository.findByName(rootname)).thenReturn(null);
    when(encoder.encode(rootpassword)).thenReturn(rootpassword);
    userService.init();
    verify(encoder, times(1)).encode(rootpassword);
    verify(constraintPersist, times(1)).add(any(), any());
  }

}