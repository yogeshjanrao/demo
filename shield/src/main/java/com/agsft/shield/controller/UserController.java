package com.agsft.shield.controller;

import com.agsft.shield.constant.Header;
import com.agsft.shield.dto.ResponseDTO;
import com.agsft.shield.dto.request.UserRequestDTO;
import com.agsft.shield.entitiy.User;
import com.agsft.shield.exception.BindingResultExceptionHandler;
import com.agsft.shield.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("shield/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BindingResultExceptionHandler bindingResultExceptionHandler;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/add-new-user",method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> addNewUser(@RequestBody UserRequestDTO userRequestDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            bindingResultExceptionHandler.bindingException(bindingResult);
        }
        User user = modelMapper.map(userRequestDTO, User.class);
        String result = userService.addUser(user);
        return ResponseEntity.ok(new ResponseDTO(200, null, result));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> loginUser(@RequestParam String userName, @RequestParam String password, @RequestHeader(value = "security", required = false) String authToken){
        String token = userService.loginUser(userName, password, authToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Auth",token);
        return new ResponseEntity<>(httpHeaders, HttpStatus.ACCEPTED);
    }

}
