package com.cd.evaluation.users.view.users.roles;

import com.cd.evaluation.users.commons.validation.user.roles.ValidRole;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    //In the future, refactor the roles to a collection in the database
    @NotBlank
    @ValidRole(message = "invalid.role")
    private String role;
}
