package com.TraperRoku.Recipe.Sharing.Platform.service;

import com.TraperRoku.Recipe.Sharing.Platform.dto.ChefDto;
import com.TraperRoku.Recipe.Sharing.Platform.dto.CredentialsDto;
import com.TraperRoku.Recipe.Sharing.Platform.dto.SignUpDto;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Chef;
import com.TraperRoku.Recipe.Sharing.Platform.exception.AppException;
import com.TraperRoku.Recipe.Sharing.Platform.mapper.ChefMapper;
import com.TraperRoku.Recipe.Sharing.Platform.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChefService {
    private final ChefRepository chefRepository;
   ChefMapper chefMapper = new ChefMapper();
    private final PasswordEncoder passwordEncoder;


    public ChefDto findByLogin(String login){
        Chef chef = chefRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Unknown chef", HttpStatus.NOT_FOUND));
        return chefMapper.toChefDto(chef);
    }

    public ChefDto login (CredentialsDto credentialsDto){
        Chef chef = chefRepository.findByLogin(credentialsDto.getLogin()).
                orElseThrow(() -> new AppException("Unknown user",HttpStatus.NOT_FOUND));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), chef.getPassword())){
            return chefMapper.toChefDto(chef);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public Chef findByLogin1(String login){
        Chef chef = chefRepository.findByLogin(login).orElseThrow(()->new RuntimeException());
        return chef;
    }


    public ChefDto register(SignUpDto signUpDto) {
        Optional<Chef> optionalChef = chefRepository.findByLogin(signUpDto.getLogin());

        if (optionalChef.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Chef chef = chefMapper.toChef(signUpDto);


        chef.setPassword(passwordEncoder.encode(new String(signUpDto.getPassword())));
        chefRepository.save(chef);
        return chefMapper.toChefDto(chef);
    }


    public Chef findByLoginChef(String login) {

        return chefRepository.findByLogin(login).orElseThrow(()->new RuntimeException("there is not chef with that login"));
    }
    public List<String> getAllChefs() {
        return chefRepository.findAll().stream()
                .map(Chef::getLogin)
                .collect(Collectors.toList());
    }
}
