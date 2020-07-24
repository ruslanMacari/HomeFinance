package homefinance.user;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacade {

  public Optional<Authentication> getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return Objects.isNull(authentication) ? Optional.empty() : Optional.of(authentication);
  }

  public Optional<String> getPrincipalName() {
    return getAuthentication().map(Principal::getName);
  }
}
