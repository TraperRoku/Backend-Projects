package com.To_Do.List.API.To_Do.List.API.controller;

import com.To_Do.List.API.To_Do.List.API.model.User;
import com.To_Do.List.API.To_Do.List.API.repository.UserRepository;
import com.To_Do.List.API.To_Do.List.API.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserRepository userRepository;
    private RoleService roleService;

    @Autowired
    public AdminController(UserRepository userRepository, RoleService roleService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        try {
            roleService.deleteUser(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete user conflict database", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
