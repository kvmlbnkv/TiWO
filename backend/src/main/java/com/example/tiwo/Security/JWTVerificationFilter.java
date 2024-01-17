package com.example.tiwo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@RequiredArgsConstructor
public class JWTVerificationFilter extends OncePerRequestFilter {

    private final JWTVerifier jwtVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String jwt;
        //System.out.println(token);
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")){
            jwt = token.substring(7);
        }
        else {
            jwt = token;
        }
        if (StringUtils.hasText(jwt)) {
            try {
                Claims body = jwtVerifier.validate(jwt);
                String username = body.getSubject();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            catch (JwtException e){
                throw new BadCredentialsException("Invalid token");
            }
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login") || request.getServletPath().equals("/tiwo/user/register");
    }

}
