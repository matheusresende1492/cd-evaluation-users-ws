package com.cd.evaluation.users.view.users.roles;

import com.cd.evaluation.users.commons.validation.user.roles.ValidRole;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class UserRoleDTO {
    @NotBlank
    @ValidRole(message = "invalid.role")
    private String role;
}
