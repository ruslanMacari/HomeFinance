package homefinance.user.login;

import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginFacade {

  private final UserService userService;
  private final ModelMapper modelMapper;

  @Autowired
  public LoginFacade(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  public boolean isAuthenticated() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return Objects.nonNull(auth)
        && auth.isAuthenticated()
        && !(auth instanceof AnonymousAuthenticationToken);
  }

  public List<String> getSimpleUsersNames() {
    return this.userService.getSimpleUsers().stream()
        .map(User::getName)
        .collect(Collectors.toList());
  }

}
