package com.example.demo.utils;


import com.example.demo.config.secret.Secret;
import com.example.demo.config.security.PrincipalDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtProvider {
    private String secretKey= Secret.JWT_SECRET_KEY;


    private PrincipalDetailsService principalDetailService;

    @Autowired
    public JwtProvider(PrincipalDetailsService principalDetailService) {
        this.principalDetailService = principalDetailService;
    }

    public String createJwt(String userEmail){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userEmail",userEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getJwt(HttpServletRequest request){
        String getToken= request.getHeader("jwt");
        return getToken;
    }


    public Authentication getAuthentication(String token){
        UserDetails userDetails= principalDetailService.loadUserByUsername(this.getUserId(token));
        userDetails.getUsername();
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserId(String token){
        String info= Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userEmail", String.class);
        return info;
    }
}
