package com.EmployeeManagement.demo.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.encrypt.key}")
    private String encryptSecret;

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtAuthFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String encryptionToken = authHeader.substring(7);
        try {
            JWEObject jweObject = JWEObject.parse(encryptionToken);

            byte [] encryptionKey = Base64.getDecoder().decode(encryptSecret);

            jweObject.decrypt(new DirectDecrypter(encryptionKey));

            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

            if(signedJWT == null) {
                throw new JOSEException("Payload is not Signed JWT");
            }

            byte[] signingKey = Base64.getDecoder().decode(jwtSecret);

            JWSVerifier jwsVerifier = new MACVerifier(signingKey);

            if(signedJWT.verify(jwsVerifier)) {
                JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
                String employeeId = signedJWT.getJWTClaimsSet().getSubject();
                String email = claims.getClaim("employeeEmail").toString();

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth/");
    }
}
