package spring_file_storage.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import spring_file_storage.demo.UserService;

/**
 * A class containing security configurations.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Sets and returns a SecurityFilterChain with security configurations such
     * as cors, csrf, JWT filter functionality, endpoint permissions etc.
     * 
     * @param http        - A HttpSecurity object
     * @param userService - A UserService object
     * @param authManager - An AuthenticationManager object
     * @return - Returns a SecurityFilterChain.
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserService userService,
            AuthenticationManager authManager) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTLoginFilter(authManager))
                .addFilterAfter(new JWTVerifyFilter(userService), JWTLoginFilter.class)
                .authorizeRequests()
                .antMatchers("/register")
                .permitAll()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/upload")
                .authenticated()
                .antMatchers("/files/*")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }

}
