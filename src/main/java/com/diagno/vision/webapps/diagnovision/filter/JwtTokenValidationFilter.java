package com.diagno.vision.webapps.diagnovision.filter;

import com.diagno.vision.webapps.diagnovision.dto.User;
import com.diagno.vision.webapps.diagnovision.repository.UserRepository;
import com.diagno.vision.webapps.diagnovision.service.JwtSigningKeyResolver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import static com.diagno.vision.webapps.diagnovision.constant.SecurityConstants.SECURITY_TOKEN_HEADER;
import static com.diagno.vision.webapps.diagnovision.constant.SecurityConstants.SECURITY_TOKEN_ISSUER;

public class JwtTokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtSigningKeyResolver signingKeyResolver;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SECURITY_TOKEN_HEADER);
        try {
            final Claims claims = extractAllClaims(token);
            final String userName = extractUserName(claims);
            final Optional<User> savedUser = userRepository.findByEmail(userName);
            if(savedUser.isPresent()){
                final Authentication auth = new UsernamePasswordAuthenticationToken(userName, null, savedUser.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch(Exception e) {
            throw new BadCredentialsException("Invalid token!");
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return false; //For now. We can add specific logic here in case, we want user to login everytime for specific apis
    }

    private String extractUserName(Claims claims) {
        return extractClaim(claims, Claims::getSubject);
    }

    private <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) throws BadCredentialsException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKeyResolver.getSigningKey())
                .requireIssuer(SECURITY_TOKEN_ISSUER)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
