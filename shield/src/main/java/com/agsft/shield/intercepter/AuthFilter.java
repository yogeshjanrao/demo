package com.agsft.shield.intercepter;

import com.agsft.shield.constant.Header;
import com.agsft.shield.dao.UserTokenRepository;
import com.agsft.shield.entitiy.UserToken;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.security.JwtProvider;
import com.agsft.shield.security.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Configuration
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = request.getHeader(Header.AUTHORIZATION.getValue());
            if (authHeader != null){
                String userName = "";
                Optional<UserToken> userToken = userTokenRepository.findByToken(authHeader);
                if (userToken.isPresent()){
                    userName = userToken.get().getUser().getEmail();
                } if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails  = this.userDetailsService.loadUserByUsername(userName);
                    if (jwtProvider.isValidToken(authHeader)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShieldException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
