package homefinance.common.config.security;

import homefinance.common.security.Role;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("userDetailsService")
  UserDetailsService userDetailsService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
      throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.debug(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    RequestMatcher csrfRequestMatcher = new RequestMatcher() {

      // Disable CSFR protection on the following urls:
      private AntPathRequestMatcher[] requestMatchers = {
          new AntPathRequestMatcher("/rest/**")
      };

      @Override
      public boolean matches(HttpServletRequest request) {
        // If the request match one url the CSFR protection will be disabled
        for (AntPathRequestMatcher rm : requestMatchers) {
          if (rm.matches(request)) {
            return true;
          }
        }
        return false;
      }
    };

    http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).disable();
    http
        .authorizeRequests()
        .antMatchers("/assets/**", "/login*", "/login/**", "/access-denied.jsp", "/rest/**")
        .permitAll()
        .antMatchers("/users/**").hasAuthority(Role.ADMIN)
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .permitAll()
        .and()
        .rememberMe()
        .and()
        .exceptionHandling().accessDeniedPage("/access-denied");

  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }

}