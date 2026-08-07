package com.EmployeeManagement.demo.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jca.JWEJCAContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;


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

    public SecretKey getSigningKey() { return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(Long employeeId, String employeeName, String employeeEmail) throws JOSEException {
        return generateToken(employeeId, employeeName, employeeEmail, jwtAccessTokenExpirationMs);
    }

    private String generateToken(Long employeeId, String employeeName, String employeeEmail, long jwtAccessTokenExpirationMs) throws JOSEException {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtAccessTokenExpirationMs);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(employeeId.toString())
                .issueTime(now)
                .expirationTime(expiry)
                .claim("employeeName", employeeName)
                .claim("employeeEmail", employeeEmail)
                .build();

        SignedJWT signedJWT = new SignedJWT( new JWSHeader(JWSAlgorithm.HS256),claims);

        JWSSigner jwsSigner = new MACSigner(jwtSecret);
        signedJWT.sign(jwsSigner);

        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM).contentType("JWT").build(),
                new Payload(signedJWT));

        JWEEncrypter encrypter = new DirectEncrypter(jwtEncryptedSecret.getBytes());
        jweObject.encrypt(encrypter);

        return jweObject.serialize();
    }
}
