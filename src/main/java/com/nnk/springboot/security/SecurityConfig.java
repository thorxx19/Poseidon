package com.nnk.springboot.security;



import com.nnk.springboot.jwt.JwtAuthenticationEntryPoint;
import com.nnk.springboot.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
    public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint handler;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"};



    @Bean
   public JwtAuthenticationFilter jwtAuthenticationFilter(){
       return new JwtAuthenticationFilter();
   }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

    @Bean
    public CorsFilter corsFilter(){
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       CorsConfiguration config = new CorsConfiguration();
       config.setAllowCredentials(true);
       config.addAllowedOrigin("http://localhost:3000/");
       config.addAllowedHeader("*");
       config.addAllowedMethod("OPTIONS");
       config.addAllowedMethod("HEAD");
       config.addAllowedMethod("GET");
       config.addAllowedMethod("PUT");
       config.addAllowedMethod("POST");
       config.addAllowedMethod("DELETE");
       config.addAllowedMethod("PATCH");
       source.registerCorsConfiguration("/**",config);
       return new CorsFilter(source);
   }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

       httpSecurity
               .cors()
               .and()
               .csrf().disable()
               .exceptionHandling().authenticationEntryPoint(handler).and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .authorizeRequests()
               .antMatchers("/actuator/**")
               .permitAll()
               .antMatchers(AUTH_WHITELIST)
               .permitAll()
               .antMatchers("/login").permitAll()
               .antMatchers("/auth/**")
               .permitAll()
               .anyRequest().authenticated()
               ;
       httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
   }

}