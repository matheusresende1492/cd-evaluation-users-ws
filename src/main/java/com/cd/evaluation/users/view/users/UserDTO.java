package com.cd.evaluation.users.view.users;

import com.cd.evaluation.users.view.users.address.UserAddressDTO;
import com.cd.evaluation.users.view.users.roles.UserRoleDTO;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

 @Id
 private String id;

 @NotBlank(message = "user.name.must.not.be.null.or.empty")
 private String name;

 @Email
 @NotBlank(message = "user.email.must.not.be.null.or.empty")
 private String email;

 @NotBlank(message = "user.password.must.not.be.null.or.empty")
 private String password;

 @NotNull(message = "user.address.must.not.be.null")
 private UserAddressDTO address;

 private String phone;

 @NotNull(message = "user.roles.must.not.be.null")
 private List<UserRoleDTO> roles = new ArrayList<>();
}
