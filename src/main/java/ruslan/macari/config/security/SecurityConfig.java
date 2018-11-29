package ruslan.macari.config.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

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
import ruslan.macari.domain.User;

@Configuration
@EnableWebMvcSecurity
@PropertySource("classpath:app.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;
    
    @Value("${db.password}")
    private String password;
    
    @Value("${db.username}")
    private String username;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/resources/**", "/login*", "/login/**", "/access-denied.jsp").permitAll()
                .antMatchers("/users/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                //.failureUrl("/login?error")
//                .usernameParameter("name")
//                .passwordParameter("password")
                .permitAll()
                .and()
                .rememberMe()
                .and()
//            .logout()
//                .permitAll()
//                //.logoutUrl("/authorization/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
                //.and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
    
    @Bean
    public Map<String, User> usersMap() {
        return new ConcurrentHashMap<>();
    }
    
    @Bean
    public User root() {
        User root = new User();
        root.setName(username);
        root.setPassword(passwordEncoder().encode(password));
        return root;
    }
    
    @Bean User unauthorized() {
        return new User("Unauthorized");
    }
}