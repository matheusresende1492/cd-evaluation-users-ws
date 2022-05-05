package com.cd.evaluation.users.model.user.roles;

import com.cd.evaluation.users.commons.validation.user.roles.ValidateRole;
import lombok.Data;

@Data
public class UserRole {
    @ValidateRole
    private String roleName;

    public UserRole(String roleName) {
        this.roleName = roleName;
    }
}
