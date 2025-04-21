package homefinance.user;

import static homefinance.user.entity.User.ROOT_NAME;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;
  private final AuthenticationService authenticationService;
  private final UserMapper userMapper;

  public List<UserDto> getUsersWithoutRoot() {
    return userService.usersExceptRoot().stream()
        .map(this::userToUserDto)
        .collect(Collectors.toList());
  }

  private UserDto userToUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setAdmin(user.isAdmin());
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setPassword(user.getPassword());
    userDto.setLoggedIn(isLoggedIn(user));
    return userDto;
  }

  private boolean isLoggedIn(User user) {
    return authenticationService.getPrincipalName().map(name -> name.equals(user.getName())).orElse(false);
  }

  public UserDto getUserById(Long id) {
    User user = userService.getById(id);
    return Objects.isNull(user) ? null : userToUserDto(user);
  }

  public void deleteUser(Long id) {
    userService.delete(id);
  }

  public void addUser(UserDto userDto) {
    userService.add(userMapper.dtoToUser(userDto));
  }

  public void update(UserDto userDto) {
    if (userDto.isPasswordChanged()) {
      userService.update(userDto);
    } else {
      userService.updateWithoutPassword(userDto);
    }
  }

  public boolean isRoot(UserDto userDto) {
    Assert.notNull(userDto, "userDto");
    return ROOT_NAME.equals(userDto.getName());
  }
}
