package ruslan.macari.service.impl;

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
import ruslan.macari.domain.UserRole;
import ruslan.macari.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	
		ruslan.macari.domain.User user = userService.getByName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
		return new User(user.getName(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
		
	}
        
       private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<>();
        userRoles.forEach((userRole) -> {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        });
        return new ArrayList<>(setAuths);
    }

}