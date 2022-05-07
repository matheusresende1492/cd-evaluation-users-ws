package com.cd.evaluation.users.builders.user;

import com.cd.evaluation.users.model.enums.address.BrazilStateEnum;
import com.cd.evaluation.users.model.enums.address.CountriesEnum;
import com.cd.evaluation.users.model.enums.roles.RoleEnum;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.model.user.address.UserAddressModel;
import com.cd.evaluation.users.model.user.roles.UserRoleModel;
import com.cd.evaluation.users.view.users.UserDTO;
import com.cd.evaluation.users.view.users.address.UserAddressDTO;
import com.cd.evaluation.users.view.users.roles.UserRoleDTO;

import java.util.List;

public class UserBuilder {

    public static UserModel buildUserModel() {
        return new UserModel(
                null,
                "Dev",
                "admin1",
                "admin1",
                new UserAddressModel(
                        80,
                        "street",
                        "neighborhood",
                        "384000000",
                        CountriesEnum.BR,
                        BrazilStateEnum.MG
                ),
                "9999999999",
                List.of(new UserRoleModel(RoleEnum.ROLE_ADMIN.toString())));
    }

    public static UserDTO buildUserDTO() {
        return new UserDTO(
                null,
                "Dev",
                "admin1@test.com",
                "admin1",
                new UserAddressDTO(
                        80,
                        "street",
                        "neighborhood",
                        "384000000",
                        CountriesEnum.BR,
                        BrazilStateEnum.MG
                ),
                "9999999999",
                List.of(new UserRoleDTO(RoleEnum.ROLE_ADMIN.toString())));
    }
}
