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
  private String rootname = "root";
  private String rootpassword = "pass";
  private PasswordEncoder encoder;
  private User user;

  @Before
  public void setUp() {
    this.userService = new UserServiceImpl();
    this.user = mock(User.class);
    this.userService.setRootName(this.rootname);
    this.userService.setRootPassword(this.rootpassword);
    this.encoder = mock(PasswordEncoder.class);
    this.userService.setEncoder(this.encoder);
  }

  @Test
  public void testInit() {
    ConstraintPersist constraintPersist = mock(ConstraintPersist.class);
    this.userService.setConstraintPersist(constraintPersist);
    UserRepository repository = mock(UserRepository.class);
    this.userService.setUserRepository(repository);
    when(repository.findByName(this.rootname)).thenReturn(this.user);
    this.userService.init();
    verify(constraintPersist, times(0)).add(any(), any());
    when(repository.findByName(this.rootname)).thenReturn(null);
    when(this.encoder.encode(this.rootpassword)).thenReturn(this.rootpassword);
    this.userService.init();
    verify(this.encoder, times(1)).encode(this.rootpassword);
    verify(constraintPersist, times(1)).add(any(), any());
  }

}