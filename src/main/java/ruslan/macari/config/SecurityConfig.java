package ruslan.macari.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("root").password("root").roles("admin")
                .and()
                .withUser("user").password("user").roles("user");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/resources/**", "/authorization/createUser", "/access-denied.jsp").permitAll()
            .antMatchers("/users/**").hasRole("admin");
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/authorization")
            .loginProcessingUrl("/authorization")
            //.defaultSuccessUrl("/users")
            .failureUrl("/authorization?error")
            .usernameParameter("name")
            .passwordParameter("password")
            .permitAll();

        http.logout()
                .permitAll()
                .logoutUrl("/authorization/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }

    @Bean
    public ShaPasswordEncoder getShaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }
}