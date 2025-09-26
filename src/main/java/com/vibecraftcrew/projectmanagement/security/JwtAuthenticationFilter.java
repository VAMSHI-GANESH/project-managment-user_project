package com.vibecraftcrew.projectmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailsService uds) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtUtils.validateToken(token)) {
                UUID userId = jwtUtils.getUserIdFromToken(token);
                var userDetails = userDetailsService.loadUserById(userId);

                // Extract roles from JWT
                var roles = jwtUtils.getRolesFromToken(token); // returns List<String>
                var authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .toList();

                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }

}
