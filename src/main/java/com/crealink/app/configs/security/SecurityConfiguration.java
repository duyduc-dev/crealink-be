package com.crealink.app.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.crealink.app.constants.AppPath;
import com.crealink.app.enums.SystemStatus;
import com.crealink.app.exceptions.UnauthorizedException;
import com.crealink.app.mapper.UserMapper;
import com.crealink.app.services.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;
    private final UserMapper userMapper;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthEntryPointException authEntryPointException;
    private final JwtService jwtService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findByExternalId(username, SystemStatus.ACTIVE)
                .map(userMapper::toDto)
                .map(AuthUser::new)
                .orElseThrow(UnauthorizedException::new);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(handlerExceptionResolver, jwtService, userDetailsService());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // we don't need CSRF because our token is invulnerable
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz ->
                authz
                    .requestMatchers(
                        HttpMethod.GET,
                        "/",
                        "/favicon.ico",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js",
                        "/*/*.png",
                        "/*/*.gif",
                        "/public/*",
                        "/*/public",
                        "/*/public/**",
                        "/*/*.json",
                        "/*/*.jpg",
                        // enable swagger endpoints
                        "/swagger-resources/*",
                        "/swagger-ui.html*",
                        "/webjars/*",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/configuration/security",
                        "/manage/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/*"
                    )
                    .permitAll()
                    .requestMatchers(
                        HttpMethod.POST,
                        AppPath.AUTH_API + AppPath.AUTH_LOGIN,
                        AppPath.AUTH_API + AppPath.AUTH_SIGNUP
                    )
                    .permitAll()
                    // allow CORS option calls
                    .requestMatchers(HttpMethod.OPTIONS, "/api/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .userDetailsService(userDetailsService())
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authEntryPointException));
        return httpSecurity.build();
    }
  
}
