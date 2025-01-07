package com.TraperRoku.Recipe.Sharing.Platform.controller;

import com.TraperRoku.Recipe.Sharing.Platform.config.UserAuthProvider;
import com.TraperRoku.Recipe.Sharing.Platform.dto.ChefDto;
import com.TraperRoku.Recipe.Sharing.Platform.dto.CredentialsDto;
import com.TraperRoku.Recipe.Sharing.Platform.dto.SignUpDto;
import com.TraperRoku.Recipe.Sharing.Platform.service.ChefService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final ChefService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<ChefDto> login(@RequestBody CredentialsDto credentialsDto){
       ChefDto userDto = userService.login(credentialsDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<ChefDto> register(@Valid @RequestBody SignUpDto SignUpDto){
        ChefDto userDto = userService.register(SignUpDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getLogin()));
        return ResponseEntity.created(URI.create("/users" + userDto.getIdChef())).body(userDto);
    }

}