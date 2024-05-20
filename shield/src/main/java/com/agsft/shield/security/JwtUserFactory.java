package com.agsft.shield.security;

import com.agsft.shield.entitiy.Role;
import com.agsft.shield.entitiy.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JwtUserFactory {

    public static JwtUser createUser(User user){
        return new JwtUser(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
                mapToGrantAuthority(new ArrayList<>(user.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrantAuthority(ArrayList<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
