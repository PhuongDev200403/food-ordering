package food_ordering.nguyenvanphuong.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

        private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

        public String generateToken (Authentication auth){
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            String roles = populateAuthorities(authorities);

            String jwt = String.valueOf(Jwts.builder()
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .claim("email", auth.getName())
                    .claim("authorities", roles)
                    .signWith(key)
                    .compact()
            );

            return jwt;
        }

    public String getEmailFromJwtToken(String token){
        token = token.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities){
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }
}
