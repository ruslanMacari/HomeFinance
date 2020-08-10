package homefinance.user.login;

import homefinance.user.AuthenticationService;
import homefinance.user.entity.User;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginFacade {

  private final UserService userService;
  private final AuthenticationService authenticationService;

  @Autowired
  public LoginFacade(UserService userService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  public boolean isAuthenticated() {
    return authenticationService.getAuthentication()
        .filter(realUserIsAuthenticated())
        .isPresent();
  }

  private Predicate<Authentication> realUserIsAuthenticated() {
    return value -> value.isAuthenticated()
        && !(value instanceof AnonymousAuthenticationToken);
  }

  public List<String> getSimpleUsersNames() {
    return userService.getSimpleUsers().stream()
        .map(User::getName)
        .collect(Collectors.toList());
  }

  public void registerUser(UserLoginDto user) {
    userService.registerUser(user.getName(), user.getPassword());
  }
}
