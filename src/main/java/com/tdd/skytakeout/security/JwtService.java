package com.tdd.skytakeout.security;

import com.tdd.skytakeout.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;


@Component
public class JwtService {
    private final JwtProperties properties;
    private final SecretKey signingKey;
    private final JwtParser jwtParser;

    public JwtService(JwtProperties properties) {
        this.properties = properties;

        //密钥，用于生成签名
        byte[] keyBytes= Decoders.BASE64.decode(properties.secret());
        this.signingKey= Keys.hmacShaKeyFor(keyBytes);

        //解析器，用于后续签名校验
        this.jwtParser= Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(properties.issuer())
                .build();
    }


    public String createToken(Long employeeId,String username){
        Instant issuedAt = Instant.now();
        //plus就是加上过期时间
        Instant expiresAt=issuedAt.plus(properties.expiration());

        return Jwts.builder()
                .issuer(properties.issuer())
                .subject(employeeId.toString())
                .claim("username",username)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .id(UUID.randomUUID().toString())
                .signWith(signingKey)
                .compact();

    }

    public Long parseEmployeeId(String token){
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        //转换成Long类型
        return Long.valueOf(claims.getSubject());
    }

}
