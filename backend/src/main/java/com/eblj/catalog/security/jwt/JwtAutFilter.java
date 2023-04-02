package com.eblj.catalog.security.jwt;

import com.eblj.catalog.services.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAutFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserServiceImpl userService;

    public JwtAutFilter(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

       String authorization = request.getHeader("Authorization");

       if(authorization!=null && authorization.startsWith("Bearer")){
          String token = authorization.split(" ")[1];
          boolean isValid = jwtService.tokenValid(token);
          if(isValid){

              String loginUsuario = jwtService.getLoginUser(token);
              UserDetails usuario= userService.loadUserByUsername(loginUsuario);
              UsernamePasswordAuthenticationToken user =
                      new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
              user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(user);

          }
       }
       filterChain.doFilter(request,response);
    }

}
