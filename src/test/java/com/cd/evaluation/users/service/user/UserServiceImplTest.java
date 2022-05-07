package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.builders.user.UserBuilder;
import com.cd.evaluation.users.commons.converter.user.UserDTOConverter;
import com.cd.evaluation.users.commons.converter.user.UserSearchDTOConverter;
import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import com.cd.evaluation.users.view.users.UserDTO;
import com.cd.evaluation.users.view.users.search.UserSearchDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    //Using the UserServiceImpl because there are functions that are not listed in the interface UserService
    private UserServiceImpl userService;

    @Mock
    private UserMongoRepository userMongoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDTOConverter userDTOConverter;

    @Mock
    private UserSearchDTOConverter userSearchDTOConverter;

    @BeforeEach
    void setUp(){
        userService = new UserServiceImpl(userMongoRepository, passwordEncoder, userDTOConverter, userSearchDTOConverter);
    }

    @Test
    void shouldLoadUserOnloadUserByUsername() {
        //given
        UserModel userModel = UserBuilder.buildUserModel();
        //providing knowledge
        Mockito.when(userMongoRepository.findByEmail("username")).thenReturn(Optional.of(userModel));
        //when
        UserDetails user = userService.loadUserByUsername("username");
        //then
        Assertions.assertNotNull(user);
        Assertions.assertEquals(userModel.getEmail(), user.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnLoadUserByUsername() {
        //given
        UsernameNotFoundException internalException =
                Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("any username"));
        //then
        Assertions.assertEquals("User not found", internalException.getMessage());
    }

    @Test
    void shouldReturnById() {
        //given
        UserModel userModel = UserBuilder.buildUserModel();
        UserDTO userDTO = UserBuilder.buildUserDTO();
        //providing knowledge
        Mockito.when(userMongoRepository.findById("id")).thenReturn(Optional.of(userModel));
        Mockito.when(userDTOConverter.convertToDTO(any(UserModel.class))).thenReturn(userDTO);
        //when
        UserDTO userDTOReturned = userService.getUserById("id");
        //then
        Assertions.assertNotNull(userDTOReturned);
        Assertions.assertEquals(userModel.getName(), userDTOReturned.getName());
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNullOnGetUserById() {
        //given
        InternalException internalException =
                Assertions.assertThrows(InternalException.class, () -> userService.getUserById(null));
        //then
        Assertions.assertEquals(400, internalException.getHttpStatus().value());
    }

    @Test
    void shouldFindByFilterWhenSearchPayloadIsNull() {
        //given
        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(UserBuilder.buildUserModel());
        //providing knowledge
        Mockito.when(userMongoRepository.findAll(PageRequest.of(0,20))).thenReturn(new PageImpl<>(userModelList));
        //when
        Page<UserModel> pageReturned = userService.getAllUsers(0, 20, null, "ASC", null);
        //then
        Assertions.assertNotNull(pageReturned);
    }

    @Test
    void shouldFindByFilterWhenSearchPayloadIsNotNull() {
        //given
        UserSearchDTO userSearchDTO = new UserSearchDTO();
        UserModel userModel = UserBuilder.buildUserModel();
        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(userModel);
        //providing knowledge
        Mockito.when(userSearchDTOConverter.convertToModelFromSearch(userSearchDTO)).thenReturn(userModel);
        Mockito.when(userMongoRepository.findAll(Example.of(userModel, ExampleMatcher.matchingAll().withIgnoreCase()), PageRequest.of(0,20))).thenReturn(new PageImpl<>(userModelList));
        //when
        Page<UserModel> pageReturned = userService.getAllUsers(0, 20, null, "ASC", userSearchDTO);
        //then
        Assertions.assertNotNull(pageReturned);
    }

    @Test
    void shouldSaveUser() {
        //given
        UserModel userModel = UserBuilder.buildUserModel();
        UserDTO userDTO = UserBuilder.buildUserDTO();
        //providing knowledge
        Mockito.when(userDTOConverter.convertToModel(any())).thenReturn(userModel);
        Mockito.when(passwordEncoder.encode(userModel.getPassword())).thenReturn("encrypted");
        Mockito.when(userDTOConverter.convertToDTO(any())).thenReturn(userDTO);
        //when
        UserDTO savedUser = userService.saveUser(userDTO);
        //then
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(userDTO, savedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserPayloadIsNullOnSaveUser() {
        //given
        InternalException internalException =
                Assertions.assertThrows(InternalException.class, () -> userService.saveUser(null));
        //then
        Assertions.assertEquals(400, internalException.getHttpStatus().value());
    }

    @Test
    void updateUser() {
        //given
        UserModel userModel = UserBuilder.buildUserModel();
        userModel.setId("id");
        UserDTO userDTO = UserBuilder.buildUserDTO();
        userDTO.setId("id");
        //providing knowledge
        Mockito.when(userDTOConverter.convertToModel(any())).thenReturn(userModel);
        Mockito.when(userMongoRepository.findById("id")).thenReturn(Optional.of(userModel));
        Mockito.when(passwordEncoder.encode(userModel.getPassword())).thenReturn("encrypted");
        Mockito.when(userDTOConverter.convertToDTO(any())).thenReturn(userDTO);
        //when
        UserDTO updatedUser = userService.updateUser(userDTO);
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(userDTO, updatedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNullOnUpdateUser() {
        //given
        UserModel userModel = UserBuilder.buildUserModel();
        userModel.setId(null);
        //providing knowledge
        Mockito.when(userDTOConverter.convertToModel(any())).thenReturn(userModel);
        //when
        InternalException internalException =
                Assertions.assertThrows(InternalException.class, () -> userService.updateUser(UserBuilder.buildUserDTO()));
        //then
        Assertions.assertEquals(400, internalException.getHttpStatus().value());
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsInvalidOnUpdateUser() {
        //given
        UserModel userModel = UserBuilder.buildUserModel();
        userModel.setId("id");
        //providing knowledge
        Mockito.when(userDTOConverter.convertToModel(any())).thenReturn(userModel);
        InternalException internalException =
                Assertions.assertThrows(InternalException.class, () -> userService.updateUser(UserBuilder.buildUserDTO()));
        //then
        Assertions.assertEquals(400, internalException.getHttpStatus().value());
    }

    @Test
    void shouldThrowExceptionWhenUserPayloadIsNullOnUpdateUser() {
        //given
        InternalException internalException =
                Assertions.assertThrows(InternalException.class, () -> userService.updateUser(null));
        //then
        Assertions.assertEquals(400, internalException.getHttpStatus().value());
    }

    @Test
    void deleteUser() {
        //given then
        Assertions.assertDoesNotThrow(() -> userService.deleteUser("id"));
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNullOnDeleteUser() {
        //given
        InternalException internalException =
                Assertions.assertThrows(InternalException.class, () -> userService.deleteUser(null));
        //then
        Assertions.assertEquals(400, internalException.getHttpStatus().value());
    }
}