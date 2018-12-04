package ruslan.macari.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component("role")
public final class Role {
    
    public final static String USER = "USER";
    public final static String ADMIN = "ADMIN";
    
    public static Set<String> getRoles() {
        Set<String> roles = new HashSet<>();
        roles.add(USER);
        roles.add(ADMIN);
        return roles;
    }
    
}
