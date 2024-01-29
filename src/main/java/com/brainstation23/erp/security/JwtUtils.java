package com.brainstation23.erp.security;


import com.brainstation23.erp.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Slf4j
@Component
public class JwtUtils {

    private static final String INVALID_TOKEN = "Invalid JWT token: {}";
    private static final String EXPIRED_TOKEN = "JWT token is expired: {}";
    private static final String UNSUPPORTED_TOKEN = "JWT token is unsupported: {}";
    private static final String EMPTY_TOKEN = "JWT claims string is empty: {}";
    private static final String jwtSecret = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";
    private static final int jwtExpirationMs = 86400000;


    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key()).build()
                    .parse(authToken);

            return true;
        } catch (MalformedJwtException e) {
            log.error(INVALID_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(EXPIRED_TOKEN, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(UNSUPPORTED_TOKEN, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(EMPTY_TOKEN, e.getMessage());
        }
        return false;
    }
}
