package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.view.users.UserDTO;
import com.cd.evaluation.users.view.users.search.UserSearchDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO getUserById(String userId) throws InternalException;

    Page<UserModel> getAllUsers(int page, int pageSize, String field, String sortDirection, UserSearchDTO userSearchDTO) throws InternalException;

    UserDTO saveUser(UserDTO userDTO) throws InternalException;

    UserDTO updateUser(UserDTO userDTO) throws InternalException;

    void deleteUser(String userId) throws InternalException;

}
