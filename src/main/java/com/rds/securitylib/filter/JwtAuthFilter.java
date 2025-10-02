package com.rds.securitylib.filter;

import com.rds.securitylib.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Set<String> skipPrefixes;
    public JwtAuthFilter(JwtService jwtService, Set<String> skipPrefixes){
        this.jwtService = jwtService;
        this.skipPrefixes = skipPrefixes;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestUri = request.getRequestURI();
        // skip filter for specific prefixes
        return skipPrefixes.stream().anyMatch(requestUri::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       String authorization = request.getHeader("Authorization");
       if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")){
           String token = authorization.substring(7);

           try {
               // parse JWT
               Jws<Claims> claims = jwtService.parse(token);
               String subject = claims.getBody().getSubject();

               // extract roles
               List<String> roles = claims.getBody().get("roles", List.class);
               var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();

               // create authentication
               var authToken = new UsernamePasswordAuthenticationToken(subject, token, authorities);
               SecurityContextHolder.getContext().setAuthentication(authToken);
           } catch (Exception e) {
               logger.error("Failed to parse JWT", e);
               // invalid JWT
               SecurityContextHolder.clearContext();
               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               return;
           }
       }
        filterChain.doFilter(request, response);
    }
}
