package com.biock.cms.security;

import com.biock.cms.config.CmsConfig;
import com.biock.cms.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public record JwtService(CmsConfig config) {

    private static final Logger LOG = LoggerFactory.getLogger(JwtService.class);

    private static final int EXPIRATION_TIME_HOURS = 4;
    private static final String CLAIM_GROUPS = "groups";

    public String generateToken(final User user) {

        final Date expirationDate = Date.from(LocalDateTime.now().plusHours(EXPIRATION_TIME_HOURS).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(user.getId())
                .setExpiration(expirationDate)
                .claim(CLAIM_GROUPS, Arrays.stream(user.getGroups()).map("ROLE_"::concat).toList())
                .signWith(getSigningKey())
                .compact();
    }

    public User parseToken(final String token) {

        try {
            final Claims jwt = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
            @SuppressWarnings("unchecked") final List<String> groups = jwt.get(CLAIM_GROUPS, List.class);
            return User.builder()
                    .id(jwt.getSubject())
                    .groups(groups.toArray(String[]::new))
                    .build();
        } catch (final JwtException e) {
            LOG.error("Error parsing JWT: {}", e.getMessage(), e);
        }
        return null;
    }

    private Key getSigningKey() {

        final byte[] secret = Base64.getDecoder().decode(this.config.getJwtSecret());
        final int keyLength = Math.max(SignatureAlgorithm.HS512.getMinKeyLength(), secret.length);
        final byte[] key = new byte[keyLength];
        Arrays.fill(key, (byte) 0);
        System.arraycopy(secret, 0, key, 0, secret.length);
        return Keys.hmacShaKeyFor(key);
    }
}
