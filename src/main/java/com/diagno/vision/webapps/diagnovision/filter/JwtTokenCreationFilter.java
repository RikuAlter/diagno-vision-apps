package com.diagno.vision.webapps.diagnovision.filter;

import com.diagno.vision.webapps.diagnovision.service.JwtSigningKeyResolver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

import static com.diagno.vision.webapps.diagnovision.constant.SecurityConstants.SECURITY_TOKEN_HEADER;
import static com.diagno.vision.webapps.diagnovision.constant.SecurityConstants.SECURITY_TOKEN_ISSUER;

public class JwtTokenCreationFilter extends OncePerRequestFilter {

    @Autowired
    JwtSigningKeyResolver signingKeyResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String userName = authentication.getName();
            final String jwt = Jwts.builder()
                    .setIssuer(SECURITY_TOKEN_ISSUER)
                    .setSubject(userName)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 600000))
                    .signWith(signingKeyResolver.getSigningKey(), SignatureAlgorithm.HS256).compact();
            response.setHeader(SECURITY_TOKEN_HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/myAccount");
    }
}
