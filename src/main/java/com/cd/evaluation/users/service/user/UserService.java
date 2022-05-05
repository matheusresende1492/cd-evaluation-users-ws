package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.model.user.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> getAllUsers();
    UserModel saveUser(UserModel userModel);
}
