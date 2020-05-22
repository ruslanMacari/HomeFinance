package homefinance.user.login;

import java.util.Objects;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginFacade {

  public boolean isAuthenticated() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return Objects.nonNull(auth)
        && auth.isAuthenticated()
        && !(auth instanceof AnonymousAuthenticationToken);
  }
}
