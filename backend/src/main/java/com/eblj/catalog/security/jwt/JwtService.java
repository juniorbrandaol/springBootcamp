package com.eblj.catalog.security.jwt;

import com.eblj.catalog.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;


    public String accessToken(User user) {

        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExp = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExp.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(chaveAssinatura)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean tokenValid(String token){
        try{
            Claims claim= getClaims(token);
            Date dataAxpiracao = claim.getExpiration();
            LocalDateTime data= dataAxpiracao
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return  !LocalDateTime.now().isAfter(data);
        }
        catch (Exception e){

            return  false;
        }
    }
    public  String getLoginUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
