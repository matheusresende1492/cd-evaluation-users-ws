package com.cd.evaluation.users.commons.converter.user;

import com.cd.evaluation.users.commons.converter.SearchDTOConverter;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.view.users.search.UserSearchDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserSearchDTOConverter extends SearchDTOConverter<UserSearchDTO, UserModel> {
    public UserSearchDTOConverter(ModelMapper modelMapper) {
        super(modelMapper, UserSearchDTO.class, UserModel.class);
    }
}
