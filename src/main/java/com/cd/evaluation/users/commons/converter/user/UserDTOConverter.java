package com.cd.evaluation.users.commons.converter.user;

import com.cd.evaluation.users.commons.converter.DTOConverter;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.view.users.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter extends DTOConverter<UserDTO, UserModel> {
    public UserDTOConverter(ModelMapper modelMapper) {
        super(modelMapper, UserDTO.class, UserModel.class);
    }
}
