package homefinance.user.service.impl;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import homefinance.common.entity.ConstraintEntity;
import homefinance.common.util.ConstraintPersist;
import homefinance.user.UserFields;
import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import homefinance.user.service.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private final String rootname = "root";
  private final String rootpassword = "pass";
  private UserServiceImpl userService;
  @Mock private User userMock;
  @Mock private PasswordEncoder encoderMock;
  @Mock private UserRepository userRepositoryMock;
  @Mock private ConstraintPersist constraintPersistMock;
  @Captor private ArgumentCaptor<Supplier<? extends ConstraintEntity>> supplierArgumentCaptor;

  @Before
  public void setUp() {
    userService = new UserServiceImpl(userRepositoryMock, constraintPersistMock, encoderMock);
    userService.setRootName(rootname);
    userService.setRootPassword(rootpassword);
  }

  @Test
  public void testInit() {
    when(userRepositoryMock.findByName(rootname)).thenReturn(userMock);
    userService.init();
    verify(constraintPersistMock, times(0)).add(any(), any());
    when(userRepositoryMock.findByName(rootname)).thenReturn(null);
    when(encoderMock.encode(rootpassword)).thenReturn(rootpassword);
    userService.init();
    verify(encoderMock, times(1)).encode(rootpassword);
    verify(constraintPersistMock, times(1)).add(any(), any());
  }

  @Test
  public void usersExceptRoot_givenRepositoryReturnsNull_returnEmptyList() {
    // given:
    given(userRepositoryMock.usersExceptRoot(rootname)).willReturn(null);
    // when:
    List<User> actual = userService.usersExceptRoot();
    // then:
    then(actual.isEmpty()).isTrue();
  }

  @Test
  public void update_givenUserFields_updateUser() {
    //given:
    UserFields userFieldsMock = mock(UserFields.class);
    given(userFieldsMock.getId()).willReturn(50);
    given(userFieldsMock.getName()).willReturn("name");
    given(userFieldsMock.getPassword()).willReturn("pass");
    given(userFieldsMock.isAdmin()).willReturn(true);
    given(userRepositoryMock.findById(50)).willReturn(Optional.of(userMock));
    Map<String, String> constraintsMap = new HashMap<>();
    given(userMock.getConstraintsMap()).willReturn(constraintsMap);
    given(encoderMock.encode("pass")).willReturn("pass encoded");
    //when:
    userService.update(userFieldsMock);
    //then:
    BDDMockito.then(userMock).should().setName("name");
    BDDMockito.then(userMock).should().setPassword("pass encoded");
    BDDMockito.then(userMock).should().setOneRole(Role.ADMIN);
    BDDMockito.then(constraintPersistMock).should().update(supplierArgumentCaptor.capture(), eq(constraintsMap));
    Supplier<? extends ConstraintEntity> lambda = supplierArgumentCaptor.getValue();
    lambda.get();
    BDDMockito.then(userRepositoryMock).should().saveAndFlush(userMock);
  }

  @Test
  public void updateWithoutPassword_givenUserFields_updateWithoutPassword() {
    //given:
    UserFields userFieldsMock = mock(UserFields.class);
    given(userFieldsMock.getId()).willReturn(60);
    given(userFieldsMock.getName()).willReturn("name1");
    given(userFieldsMock.isAdmin()).willReturn(false);
    given(userRepositoryMock.findById(60)).willReturn(Optional.of(userMock));
    Map<String, String> constraintsMap = new HashMap<>();
    given(userMock.getConstraintsMap()).willReturn(constraintsMap);
    //when:
    userService.updateWithoutPassword(userFieldsMock);
    //then:
    BDDMockito.then(userMock).should().setName("name1");
    BDDMockito.then(userMock).should(never()).setPassword(anyString());
    BDDMockito.then(userMock).should().setOneRole(Role.USER);
    BDDMockito.then(constraintPersistMock).should().update(supplierArgumentCaptor.capture(), eq(constraintsMap));
    Supplier<? extends ConstraintEntity> lambda = supplierArgumentCaptor.getValue();
    lambda.get();
    BDDMockito.then(userRepositoryMock).should().saveAndFlush(userMock);
  }
}