package com.x.twitter.Security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.x.twitter.Security.SecurityExceptions.InvalidTokenException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {


    private final Algorithm algorithm=Algorithm.HMAC256("!sE3N?5'v&/'i<]`<c4a3SN#oKelyNcQ");



    public String generateToken(UserDetails user){



        String token= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+10+60*1000))
                .withClaim("authorities",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);


        return "Bearer ".concat(token);

    }
    public Boolean validateToken(String token, UserDetails userDetails) throws InvalidTokenException {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            String username = extractUser(token);
            Date expirationDate = extractExpiration(token);
            return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
        } catch (JWTVerificationException exception){
            throw new InvalidTokenException("token is invalid.");
        }
    }
    private Date extractExpiration(String token) {
        return JWT.decode(token).getExpiresAt();

    }
    public String extractUser(String token) {
        return JWT.decode(token).getSubject();
    }


}
