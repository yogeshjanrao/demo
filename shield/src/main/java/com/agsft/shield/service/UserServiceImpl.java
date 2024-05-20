package com.agsft.shield.service;

import com.agsft.shield.dao.UserRepository;
import com.agsft.shield.dao.UserTokenRepository;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.User;
import com.agsft.shield.entitiy.UserToken;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.security.JwtProvider;
import com.agsft.shield.util.ShieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Override
    public String addUser(User user) {
        User loggedInUser = jwtProvider.getLoggedInUser();
        if (!jwtProvider.isAdmin(loggedInUser)){
            throw new ShieldException(HttpStatus.NOT_ACCEPTABLE, "Unauthorized !!");
        }
        if (jwtProvider.isAdmin(user)) {
            throw new ShieldException(HttpStatus.BAD_REQUEST, "Cannot add admin here !!");
        }
        User alreadyRegisteredUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (Objects.nonNull(alreadyRegisteredUser)) {
            throw new ShieldException(HttpStatus.BAD_REQUEST, String.format("'%s' id already registered ", user.getEmail()));
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        User savedUser = userRepository.save(user);
        if (Objects.nonNull(savedUser.getEmail())) {
            return "User saved successfully !!";
        } else {
            throw new ShieldException(HttpStatus.INTERNAL_SERVER_ERROR, "Error !!");
        }
    }

    @Override
    public String loginUser(String userName, String password, String authToken) {
        User user = userRepository.findByEmail(userName).orElse(null);
        if (user == null){
            throw new ShieldException(HttpStatus.BAD_REQUEST, "Invalid user name");
        }
        if (authToken != null){
            if (jwtProvider.isValidToken(authToken)){
                return authToken;
            }
        }
        String jwtToken = null;
        if (passwordEncoder.matches(password, user.getPassword())) {
            jwtToken = jwtProvider.generateToken(user);
            UserToken userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(jwtToken);
            userToken.setTokenType("JWT");
            userToken.setExpirationTime(ShieldUtil.getTokenExpiration());
            userTokenRepository.save(userToken);
        } else {
            throw new ShieldException(HttpStatus.BAD_REQUEST, "Invalid password !!");
        }
        return jwtToken;
    }

    @Override
    public User findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ShieldException(HttpStatus.NOT_FOUND, "userId", "Invalid User Id !!", "User Not Found for Entered Id !!"));
        return user;
    }
}
