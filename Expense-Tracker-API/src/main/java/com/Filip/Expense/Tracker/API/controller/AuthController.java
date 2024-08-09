package com.Filip.Expense.Tracker.API.controller;

import com.Filip.Expense.Tracker.API.dto.LoginDto;
import com.Filip.Expense.Tracker.API.model.User;
import com.Filip.Expense.Tracker.API.dto.AuthDto;
import com.Filip.Expense.Tracker.API.security.JwtUtil;
import com.Filip.Expense.Tracker.API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> login(@RequestBody User user){
        if(userService.findByUsername(user.getUsername())!=null){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        userService.registerUser(user);
        return new ResponseEntity<>("User registered succesfully",HttpStatus.OK);
    }

   @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
        );
       SecurityContextHolder.getContext().setAuthentication(authentication);
       String token = jwtUtil.generateToken((UserDetails)authentication.getPrincipal());

       AuthDto responseDto = new AuthDto(token);

       return ResponseEntity.ok(responseDto);
   }

}
