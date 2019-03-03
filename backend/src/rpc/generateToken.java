package rpc;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class generateToken {

    public long EXPIRE_TIME = 18000000;
    public String ISSUER = "travelpin";
    private static Key key = MacProvider.generateKey();
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        JwtBuilder builder = Jwts.builder().setId(id).setIssuer(issuer).setSubject(subject).signWith(signatureAlgorithm, key);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static void parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }
}
