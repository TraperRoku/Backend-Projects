package com.To_Do.List.API.To_Do.List.API.service;

import com.To_Do.List.API.To_Do.List.API.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private UserRepository userRepository;

    @Autowired
    public RoleService(UserRepository userRepository){
        this.userRepository =userRepository;
    }

    @Transactional
    public void deleteUser(int userId){
        userRepository.deleteRolesByUserId(userId);
        userRepository.deleteById(userId);
    }
}
