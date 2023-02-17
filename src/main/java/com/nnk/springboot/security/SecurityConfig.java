package com.nnk.springboot.security;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author froidefond
 */
@Configuration
@EnableWebSecurity
    public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("ADMIN")
                .and().formLogin().and().oauth2Login().and().exceptionHandling().accessDeniedPage("/403")
        ;
        return http.build();
    }
}