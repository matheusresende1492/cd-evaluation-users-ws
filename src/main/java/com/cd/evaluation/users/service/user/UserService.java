package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.view.users.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(String userId) throws InternalException;

    List<UserDTO> getAllUsers() throws InternalException;

    UserDTO saveUser(UserDTO userDTO) throws InternalException;

    UserDTO updateUser(UserDTO userDTO) throws InternalException;

    void deleteUser(String userId) throws InternalException;

}
