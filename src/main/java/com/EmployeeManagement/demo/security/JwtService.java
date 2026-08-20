package com.EmployeeManagement.demo.security;

import com.EmployeeManagement.demo.entities.Employee;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtService {

    @Value("${app.jwt.secret}")
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

    public SecretKey getSigningKey() { return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(Employee user) throws JOSEException {
        return generateToken(user.getId(), user.getUsername(), jwtAccessTokenExpirationMs);
    }

    private String generateToken(Long employeeId, String employeeEmail, long jwtAccessTokenExpirationMs) throws JOSEException {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtAccessTokenExpirationMs);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(employeeId.toString())
                .issueTime(now)
                .expirationTime(expiry)
                .claim("employeeEmail", employeeEmail)
                .build();

        SignedJWT signedJWT = new SignedJWT( new JWSHeader(JWSAlgorithm.HS256),claims);

        byte[] signingKey = Base64.getDecoder().decode(jwtSecret);
        JWSSigner jwsSigner = new MACSigner(signingKey);
        signedJWT.sign(jwsSigner);

        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM).contentType("JWT").build(),
                new Payload(signedJWT));

        byte[] encKey = Base64.getDecoder().decode(jwtEncryptedSecret);
        JWEEncrypter encrypter = new DirectEncrypter(encKey);
        jweObject.encrypt(encrypter);

        return jweObject.serialize();
    }

    private void validateToken(){

    }
}
