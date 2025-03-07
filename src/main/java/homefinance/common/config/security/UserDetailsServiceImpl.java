package homefinance.common.config.security;

import static java.lang.String.format;

import homefinance.user.entity.UserRole;
import homefinance.user.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    homefinance.user.entity.User user = getUserBy(username);
    List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
    return new User(user.getName(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
  }

  private homefinance.user.entity.User getUserBy(String username) {
    homefinance.user.entity.User user = userService.getByName(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException(format("user by username: %s not found", username));
    }
    return user;
  }

  private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
    Set<GrantedAuthority> setAuths = new HashSet<>();
    userRoles.forEach((userRole) -> setAuths.add(new SimpleGrantedAuthority(userRole.getRole().name())));
    return new ArrayList<>(setAuths);
  }

}
