package homefinance.user;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {

  private final UserService userService;

  @Autowired
  public UserFacade(UserService userService) {
    this.userService = userService;
  }

  public List<UserDto> getUsersWithoutRoot() {
    return userService.usersExceptRoot().stream()
        .map(this::userToUserDto)
        .collect(Collectors.toList());
  }

  private UserDto userToUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setAdmin(user.hasAdmin());
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setPassword(user.getPassword());
    return userDto;
  }


  public UserDto getUserById(int id) {
    User user = userService.getById(id);
    return Objects.isNull(user) ? null : userToUserDto(user);
  }

  public void deleteUser(int id) {
    userService.delete(id);
  }
}
