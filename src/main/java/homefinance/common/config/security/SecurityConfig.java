package homefinance.common.config.security;

import homefinance.user.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/assets/**", "/login*", "/login/**", "/rest/**").permitAll()
                .requestMatchers("/users/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
        )
        .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/"))
        .rememberMe(rememberMe -> {})
        .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));
    return http.build();
  }

}
