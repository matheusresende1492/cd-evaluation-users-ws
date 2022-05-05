package com.cd.evaluation.users.model.user;

import com.cd.evaluation.users.model.user.address.UserAddress;
import com.cd.evaluation.users.model.user.roles.UserRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class UserModel {

    @Id
    private String id;
    @NotBlank(message = "User name must not be null or empty.")
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private UserAddress userAddress;
    private String phone;
    @NotNull
    private List<UserRole> roles = new ArrayList<>();

    public UserModel(String name, String email, String password, UserAddress userAddress, String phone, List<UserRole> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userAddress = userAddress;
        this.phone = phone;
        this.roles = roles;
    }
}
