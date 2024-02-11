package com.mercy.expensetracker.service;

import com.mercy.expensetracker.model.User;
import com.mercy.expensetracker.model.UserModel;

public interface UserService {
    User createUser(UserModel user);
}
