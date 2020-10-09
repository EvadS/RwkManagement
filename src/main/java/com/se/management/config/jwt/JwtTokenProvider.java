package com.se.management.config.jwt;

import com.se.management.model.CustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


// TODO: all logic for work with token
@Component
public class JwtTokenProvider  {
    private final static Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
     @Value("${security.jwt.uri:/auth/login}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private long jwtExpiration;

    public static final String ROLES = "ROLES";

    public String getSecret() {
        return secret;
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUri() {
        return Uri;
    }

    public JwtTokenProvider(@Value("${app.jwt.secret}") String jwtSecret, @Value("${app.jwt.expiration}") int jwtExpiration) {
        this.secret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    public String createToken(Authentication auth) {
        long now = System.currentTimeMillis();
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", authorities)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String generateToken(Map<String, Object> claims, String subject) {
        final long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtExpiration * 1000))

                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();


    }

    //generate token for user
    public String generateToken(Authentication authentication) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();

        final List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put(ROLES, roles);
        return generateToken(claims, user.getUsername());
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    /**
     * Returns the user id encapsulated within the token
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Generates a token from a principal object. Embed the refresh token in the jwt
     * so that a new jwt can be created
     */

    // TODO: unifique logic
    public String generateTokenFromUserId(Long userId) {
        Instant expiryDate = Instant.now().plusMillis(jwtExpiration);
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }



    /**
     * Returns the token expiration date encapsulated within the token
     */
    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    /**
     * Return the jwt expiration for the client so that they can execute
     * the refresh token logic appropriately
     */
    public long getExpiryDuration() {
        return jwtExpiration;
    }


    public List<String> getRoles(String token) {
        return getClaimFromToken(token, claims -> (List) claims.get(ROLES));
    }



    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
