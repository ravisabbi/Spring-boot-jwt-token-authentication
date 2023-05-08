//package com.example.taskproject.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtProvider {
//    public String generateToken(Authentication authentication){
//        String email = authentication.getName();
//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + 3600000);//milliseconds
//        String token = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS512, "JWTSecureKey".getBytes())
//                .setSubject(email)
//                .setIssuedAt(currentDate)
//                .setExpiration(expireDate)
//
//                .compact();
//        return token;
//    }
//
//    public  String getEmailFromToken(String token){
//       Claims claims =  Jwts.parser().setSigningKey("JWTSecureKey")
//                .parseClaimsJws(token).getBody();
//       return claims.getSubject();
//    }
//
//}

package com.example.taskproject.security;

import com.example.taskproject.exception.APIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 3600000);//milliseconds
        String token = Jwts.builder()
                .signWith(key)
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .compact();
        return token;
    }

    public  String getEmailFromToken(String token){
        Claims claims =  Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){

        try{
            Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            throw new APIException("Token issue" + e.getMessage());

        }

    }
}
