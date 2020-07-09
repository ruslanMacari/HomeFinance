package homefinance.user;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

public class UserFacade {

  private final UserService userService;

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


}
