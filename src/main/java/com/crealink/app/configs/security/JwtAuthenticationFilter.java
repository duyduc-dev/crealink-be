package com.crealink.app.configs.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.crealink.app.constants.CookiesKey;
import com.crealink.app.exceptions.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = extractTokenFromCookie(request);
        if (token != null) {
            try {
                authenticateUser(token, request);
            } catch (Exception ex) {
                handlerExceptionResolver.resolveException(request, response, null, ex);
                return; // stop chain because exception is handled
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Validate token and set Authentication in SecurityContext
     */
    private void authenticateUser(String token, HttpServletRequest request) {
        String userId = jwtService.extractUserExternalId(token);
        if (userId == null) {
            throw new UnauthorizedException("Invalid token: user not found in token");
        }

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null) {
            return; // already authenticated → skip
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        if (!jwtService.validateToken(token)) {
            throw new UnauthorizedException("Token is not valid or expired");
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    private String extractTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) return null;

    for (Cookie cookie : request.getCookies()) {
        if (CookiesKey.ACCESS_TOKEN.equals(cookie.getName())) {
            return cookie.getValue();
        }
    }
    return null;
    }

}
