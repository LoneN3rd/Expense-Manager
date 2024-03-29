package com.mercy.expensetracker.service.impl;

import com.mercy.expensetracker.exception.ItemAlreadyExistsException;
import com.mercy.expensetracker.exception.ResourceNotFoundException;
import com.mercy.expensetracker.model.User;
import com.mercy.expensetracker.model.UserModel;
import com.mercy.expensetracker.repository.UserRepository;
import com.mercy.expensetracker.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel user) throws ItemAlreadyExistsException{
        if (userRepository.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExistsException("A user with email '"+ user.getEmail() +"' exists");
        }
        System.out.println("Creating user...");
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setCreatedBy("mmutuku");
        return userRepository.save(newUser);
    }
}
