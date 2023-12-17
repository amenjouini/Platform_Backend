package com.amen.platform.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

//    @Value("${application.security.jwt.refresh-token.expiration}")
//    private long jwtAdminManagerExpiration;

    public String generateForgetToken(String email) {
        return buildToken(email, jwtExpiration);
    }

//    public String generateAdminManagerToken(String email) {
//        return buildToken(email, jwtAdminManagerExpiration);
//    }

    public String getEmailFromToken(String token)throws MessagingException {
        //try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//        } catch (Exception e) {
//            return null;
//        }
    }

    private String buildToken(String subject, long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}