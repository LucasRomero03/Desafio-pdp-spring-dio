package com.lrtech.desafio_padroes_de_projeto.Services.Auth;

import com.lrtech.desafio_padroes_de_projeto.Services.ClienteServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ClienteServiceImpl clienteService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,  IOException {
        var token = this.recoverToken(request);
        String requestUri = request.getRequestURI();

        if (requestUri.equals("/auth/login") || requestUri.equals("/auth/registrar") || requestUri.startsWith("/h2-console")
                || requestUri.startsWith("/client")){
            filterChain.doFilter(request, response);
            return;
        }
        //CASO O USUARIO NAO FORNEÇA token
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("usuario nao autenticado");
            response.getWriter().flush();
            return;
        }
        if (token != null) {
            try {
                var subject = tokenService.validateToken(token);
                UserDetails user = clienteService.findByEmail(subject);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.getWriter().write("token invalido ");
                response.getWriter().flush();
            }

        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

}