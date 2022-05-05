package com.cd.evaluation.users.view.users;

import com.cd.evaluation.users.model.enums.roles.RoleEnum;
import com.cd.evaluation.users.model.user.address.UserAddress;
import lombok.Data;

@Data
public class UserDTO {
 private String id;

 private String name;

 private String email;

 private String password;

 private UserAddress userAddress;

 private String phone;

 private RoleEnum role;
}
