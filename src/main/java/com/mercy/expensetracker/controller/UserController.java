package com.mercy.expensetracker.controller;

import com.mercy.expensetracker.model.User;
import com.mercy.expensetracker.model.UserModel;
import com.mercy.expensetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserModel user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
}
