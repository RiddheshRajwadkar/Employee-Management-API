package com.EmployeeManagement.demo.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class jwtService {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwt.encrypt.key}")
    private String jwtEncryptedSecret;

    @Value("${app.jwt.access-token-expiration-ms}")
    private long jwtAccessTokenExpirationMs;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private long jwtRefreshTokenExpirationMs;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public long getJwtAccessTokenExpirationMs() {
        return jwtAccessTokenExpirationMs;
    }

    public void setJwtAccessTokenExpirationMs(long jwtAccessTokenExpirationMs) {
        this.jwtAccessTokenExpirationMs = jwtAccessTokenExpirationMs;
    }

    public long getJwtRefreshTokenExpirationMs() {
        return jwtRefreshTokenExpirationMs;
    }

    public void setJwtRefreshTokenExpirationMs(long jwtRefreshTokenExpirationMs) {
        this.jwtRefreshTokenExpirationMs = jwtRefreshTokenExpirationMs;
    }

    private SecretKey getSigningKey() {
            return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private String generateAccessToken(Long employeeId, String name, String email, String role) throws JOSEException{
        return generateToken(employeeId, name, email, role, jwtAccessTokenExpirationMs);
    }

    private String generateToken(Long employeeId, String name, String email, String role, long jwtAccessTokenExpirationMs) throws JOSEException {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtAccessTokenExpirationMs);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(employeeId.toString())
                .issueTime(now)
                .expirationTime(expiry)
                .claim("name", name)
                .claim("email", email)
                .claim("role", role)
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        JWSSigner jwsSigner = new MACSigner(jwtSecret);

        signedJWT.sign(jwsSigner);

        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256CBC_HS512).contentType("JWT")
                .build(),new Payload(signedJWT));

        byte[] encrptionKey = Base64.getDecoder().decode(jwtEncryptedSecret);

        if (encrptionKey.length != 64) {
            throw new RuntimeException("The Encrption needs to be 64 bytes long");
        }
        JWEEncrypter encrypter = new DirectEncrypter(encrptionKey);
        jweObject.encrypt(encrypter);

        return jweObject.serialize();
    }

    public Long getEmployeeNameFromToken(String token){ return Long.parseLong(extractClaims(token, Claims::getSubject));}

    private String extractClaims(String token, Object getSubject) {
        final Claims claims = extractAllClaims(token);
    }

    private Claims extractAllClaims(String token) {
    try{
        return Jwts.parserBuilder()
                .
    }
    }
}
