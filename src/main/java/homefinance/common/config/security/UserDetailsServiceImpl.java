package homefinance.common.config.security;

import static java.lang.String.format;

import homefinance.user.entity.UserRole;
import homefinance.user.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    homefinance.user.entity.User user = getUserBy(username);
    return User.builder()
        .username(user.getName())
        .password(user.getPassword())
        .disabled(!user.isEnabled())
        .accountExpired(false)
        .credentialsExpired(false)
        .accountLocked(false)
        .authorities(convertRolesToAuthorities(user.getUserRole()))
        .build();
  }

  private homefinance.user.entity.User getUserBy(String username) {
    homefinance.user.entity.User user = userService.getByName(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException(format("user by username: %s not found", username));
    }
    return user;
  }

  private List<GrantedAuthority> convertRolesToAuthorities(Set<UserRole> userRoles) {
    return userRoles.stream()
        .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
        .map(GrantedAuthority.class::cast)
        .distinct()
        .toList();
  }

}
