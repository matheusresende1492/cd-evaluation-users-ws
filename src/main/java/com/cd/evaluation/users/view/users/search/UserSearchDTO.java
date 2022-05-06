package com.cd.evaluation.users.view.users.search;

import com.cd.evaluation.users.model.user.roles.UserRoleModel;
import lombok.Data;

import java.util.List;

/**
 * User search class for filter search
 */
@Data
public class UserSearchDTO {
    private String id;

    private String name;

    private String email;

    private UserAddressSearchDTO address;

    private String phone;

    private List<UserRoleModel> roles;
}
