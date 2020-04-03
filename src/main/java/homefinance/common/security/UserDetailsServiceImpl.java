package homefinance.common.security;

import homefinance.user.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

    homefinance.common.security.User user = userService.getByName(username);
    List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
    return new User(user.getName(), user.getPassword(), user.isEnabled(), true, true, true,
        authorities);

  }

  private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

    Set<GrantedAuthority> setAuths = new HashSet<>();
    userRoles.forEach((userRole) -> {
      setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
    });
    return new ArrayList<>(setAuths);
  }

}
