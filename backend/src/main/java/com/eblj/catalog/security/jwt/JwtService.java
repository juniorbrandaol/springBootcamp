package com.eblj.catalog.security.jwt;

import com.eblj.catalog.entities.Users;
import com.eblj.catalog.servicies.exceptions.TokenInvalidException;
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
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;


    public String accessToken(Users user) {

        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExp = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExp.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        HashMap<String,Object> claims = new HashMap<>();

        return Jwts
                .builder()
                .setSubject(user.getEmail()) //obrigatório
                .setExpiration(data)
                //obrigatório
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {

        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean tokenValid(String token){
        try{

            Claims claim= getClaims(token);
            System.out.println("Claim "+claim);
            Date dataAxpiracao = claim.getExpiration();
            LocalDateTime data= dataAxpiracao
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return  !LocalDateTime.now().isAfter(data);
        }
        catch (ExpiredJwtException e){
            throw new TokenInvalidException();
        }
    }
    public  String getLoginUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
