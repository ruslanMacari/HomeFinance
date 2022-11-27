package homefinance.common.config.security;

import homefinance.user.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserDetailsService userDetailsService;

  public SecurityConfig(@Qualifier("userDetailsService") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder encoder) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(encoder);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .requireCsrfProtectionMatcher(getCsrfRequestMatcher()).disable()
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/assets/**", "/login*", "/login/**", "/rest/**").permitAll()
                .requestMatchers("/users/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
        )
        .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/"))
        .rememberMe()
        .and().exceptionHandling().accessDeniedPage("/access-denied");
    return http.build();
  }

  private RequestMatcher getCsrfRequestMatcher() {
    return new RequestMatcher() {

      // Disable CSFR protection on the following urls:
      private final AntPathRequestMatcher[] requestMatchers = {
          new AntPathRequestMatcher("/rest/**")
      };

      @Override
      public boolean matches(HttpServletRequest request) {
        // If the request match one url the CSFR protection will be disabled
        return Arrays.stream(requestMatchers).anyMatch(rm -> rm.matches(request));
      }
    };
  }

}
