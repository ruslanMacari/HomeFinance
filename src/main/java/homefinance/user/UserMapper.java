package homefinance.user;

import homefinance.user.entity.Role;
import homefinance.user.entity.User;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

  private final PasswordEncoder encoder;

  @Autowired
  public UserMapper(PasswordEncoder encoder) {
    this.encoder = encoder;
  }

  public User dtoToUser(UserDto userDto) {
    Assert.notNull(userDto, "userDto");
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setPassword(encoder.encode(userDto.getPassword()));
    user.setEnabled(true);
    user.setOneRole(userDto.isAdmin() ? Role.ADMIN : Role.USER);
    return user;
  }
}
