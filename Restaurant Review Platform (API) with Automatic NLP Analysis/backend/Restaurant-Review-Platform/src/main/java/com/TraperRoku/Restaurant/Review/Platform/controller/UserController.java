package com.TraperRoku.Restaurant.Review.Platform.controller;


import com.TraperRoku.Restaurant.Review.Platform.config.UserAuthProvider;
import com.TraperRoku.Restaurant.Review.Platform.dto.CredentialsDto;
import com.TraperRoku.Restaurant.Review.Platform.dto.SignUpDto;
import com.TraperRoku.Restaurant.Review.Platform.dto.UserDto;
import com.TraperRoku.Restaurant.Review.Platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentialsDto){
        UserDto login = userService.login(credentialsDto);
        login.setToken(userAuthProvider.createToken(login.getLogin()));

        return ResponseEntity.ok(login);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody SignUpDto signUpDto){
        UserDto register = userService.register(signUpDto);


        register.setToken(userAuthProvider.createToken(register.getLogin()));
        return ResponseEntity.ok(register); // !!!
    }
}
