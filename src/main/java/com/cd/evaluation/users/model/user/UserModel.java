package com.cd.evaluation.users.model.user;

import com.cd.evaluation.users.model.user.address.UserAddressModel;
import com.cd.evaluation.users.model.user.roles.UserRoleModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private UserAddressModel address;

    private String phone;

    private List<UserRoleModel> roles = new ArrayList<>();
}
