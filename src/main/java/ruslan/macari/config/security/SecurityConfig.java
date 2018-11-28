package ruslan.macari.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("root").password("root").roles("admin")
//                .and()
//                .withUser("user").password("user").roles("user");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/resources/**", "/authorization/createUser", "/authorization/saveUser", "/access-denied.jsp", "/authorization*").permitAll()
                .antMatchers("/users/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/authorization")
                .loginProcessingUrl("/authorization")
                .defaultSuccessUrl("/")
                .failureUrl("/authorization?error")
                .usernameParameter("name")
                .passwordParameter("password")
                .permitAll()
                .and()
                .rememberMe()
                .and()
            .logout()
                .permitAll()
                .logoutUrl("/authorization/logout")
                .logoutSuccessUrl("/authorization?logout")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}