package com.TraperRoku.backend.controller;

import com.TraperRoku.backend.Dto.CredentialsDto;
import com.TraperRoku.backend.Dto.SignUpDto;
import com.TraperRoku.backend.Dto.UserDto;
import com.TraperRoku.backend.config.UserAuthProvider;
import com.TraperRoku.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto userDto = userService.login(credentialsDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto SignUpDto){
        UserDto userDto = userService.register(SignUpDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getLogin()));
        return ResponseEntity.created(URI.create("/users" + userDto.getId())).body(userDto);
    }
}
