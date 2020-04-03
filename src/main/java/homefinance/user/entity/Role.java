package homefinance.user.entity;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component("role")
public final class Role {

  public final static String USER = "USER";
  public final static String ADMIN = "ADMIN";
  private static final Set<String> roles = new HashSet<>();

  static {
    roles.add(USER);
    roles.add(ADMIN);
  }

  public static Set<String> getRoles() {
    return roles;
  }

}
