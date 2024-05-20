package com.agsft.shield.security;

import com.agsft.shield.dao.UserRepository;
import com.agsft.shield.dao.UserTokenRepository;
import com.agsft.shield.entitiy.User;
import com.agsft.shield.entitiy.UserToken;
import com.agsft.shield.util.ShieldUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(User user){
        String authority = new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList()))
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", user.getEmail());
        claims.put("roles", user.getRoles());
        claims.put("auth", authority);

        byte [] keyBytes;
        if (!StringUtils.isEmpty(secret)){
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            keyBytes = Decoders.BASE64.decode(secret);
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(ShieldUtil.getTokenExpiration())
                .signWith(SignatureAlgorithm.HS256, key).compact();
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUserNameFromToken(String token){
        String userName;
        try{
            Claims claims = getClaimsFromToken(token);
            userName = (String) claims.get("userName");
        } catch (Exception e){
            userName = null;
        }
        return userName;
    }

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        Optional<User> user = userRepository.findByEmail(jwtUser.getUsername());
        return user.orElse(null);
    }

    public Boolean isAdmin(User user){
        return user.getRoles()
                .stream()
                .anyMatch(role->role.getRole().equals("ADMIN"));
    }

    public Boolean isValidToken(String token){
        boolean isValid = false;
        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if (userToken.isPresent()){
            if (userToken.get().getExpirationTime().after(new Date())){
                isValid = true;
            }
        }
        return isValid;
    }

}
