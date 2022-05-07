package com.cd.evaluation.users.controller.user;

import com.cd.evaluation.users.builders.user.UserBuilder;
import com.cd.evaluation.users.config.security.filter.algorithm.AlgorithmManager;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import com.cd.evaluation.users.service.user.UserService;
import com.cd.evaluation.users.view.users.UserDTO;
import com.cd.evaluation.users.view.users.search.UserSearchDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String USERNAME = "admin";
    private static final String USERS_API_BASE_URL = "/api/v1/users";

    @Autowired
    private MockMvc mvc;
    @MockBean
    UserService userService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    AlgorithmManager algorithmManager;

    @MockBean
    UserMongoRepository userMongoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    //Testing get by ID for user with ROLE_ADMIN profile
    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_ADMIN })
    void shouldGetByIdForRoleAdmin() throws Exception {
        //providing knowledge
        Mockito.when(userService.getUserById(anyString())).thenReturn(UserBuilder.buildUserDTO());
        //when
        mvc.perform(get(USERS_API_BASE_URL + "/id"))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    //testing request with no authentication
    @Test
    void shouldNotGetByIdWhenThereIsNoAuthentication() throws Exception {
        //when
        mvc.perform(get(USERS_API_BASE_URL + "/id"))
                .andDo(print())
                //then
                .andExpect(status().isForbidden());
    }

    //Testing get by ID for user with ROLE_USER profile
    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_USER })
    void shouldGetByIdForRoleUser() throws Exception {
        //providing knowledge
        Mockito.when(userService.getUserById(anyString())).thenReturn(UserBuilder.buildUserDTO());
        //when
        mvc.perform(get(USERS_API_BASE_URL + "/id"))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_ADMIN })
    void shouldGetAllUsersForRoleAdmin() throws Exception {
        //given
        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(UserBuilder.buildUserModel());
        //providing knowledge
        Mockito.when(userService.getAllUsers(anyInt(), anyInt(), anyString(), anyString(), any(UserSearchDTO.class))).thenReturn(new PageImpl<>(userModelList));
        //when
        mvc.perform(get(USERS_API_BASE_URL))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_USER })
    void shouldGetAllUsersForRoleUser() throws Exception {
        //given
        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(UserBuilder.buildUserModel());
        //providing knowledge
        Mockito.when(userService.getAllUsers(anyInt(), anyInt(), anyString(), anyString(), any(UserSearchDTO.class))).thenReturn(new PageImpl<>(userModelList));
        //when
        mvc.perform(get(USERS_API_BASE_URL))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    //testing post request with no authentication
    @Test
    void shouldNotGetAllUsersWhenThereIsNoAuthentication() throws Exception {
        //given
        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(UserBuilder.buildUserModel());
        //providing knowledge
        Mockito.when(userService.getAllUsers(anyInt(), anyInt(), anyString(), anyString(), any(UserSearchDTO.class))).thenReturn(new PageImpl<>(userModelList));
        //when
        mvc.perform(get(USERS_API_BASE_URL))
                .andDo(print())
                //then
                .andExpect(status().isForbidden());
    }

    //Testing save for user with ROLE_ADMIN profile
    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_ADMIN })
    void shouldSaveUserForRoleAdmin() throws Exception {
        //given
        UserDTO userDTO = UserBuilder.buildUserDTO();
        String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
        //providing knowledge
        Mockito.when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        //when
        MvcResult result = mvc.perform(
                post(USERS_API_BASE_URL)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonUserDTO)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(jsonUserDTO, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_USER })
    void shouldNotSaveUserForRoleUser() throws Exception {
        //given
        UserDTO userDTO = UserBuilder.buildUserDTO();
        String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
        //providing knowledge
        Mockito.when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        //when
        MvcResult result = mvc.perform(
                        post(USERS_API_BASE_URL)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(jsonUserDTO)
                                .contentType(APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isForbidden()).andReturn();
        Assertions.assertNotNull(result);
    }

    //testing post request with no authentication
    @Test
    void shouldNotSaveUserWhenThereIsNoAuthentication() throws Exception {
        //given
        UserDTO userDTO = UserBuilder.buildUserDTO();
        String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
        //providing knowledge
        Mockito.when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        //when
        MvcResult result = mvc.perform(
                        post(USERS_API_BASE_URL)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(jsonUserDTO)
                                .contentType(APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isForbidden()).andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_ADMIN })
    void shouldUpdateUserForRoleAdmin() throws Exception {
        //given
        UserDTO userDTO = UserBuilder.buildUserDTO();
        String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
        //providing knowledge
        Mockito.when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);
        //when
        MvcResult result = mvc.perform(
                        put(USERS_API_BASE_URL)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(jsonUserDTO)
                                .contentType(APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(jsonUserDTO, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_USER })
    void shouldNotUpdateUserForRoleUser() throws Exception {
        //given
        UserDTO userDTO = UserBuilder.buildUserDTO();
        String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
        //providing knowledge
        Mockito.when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);
        //when
        MvcResult result = mvc.perform(
                        put(USERS_API_BASE_URL)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(jsonUserDTO)
                                .contentType(APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isForbidden()).andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void shouldNotUpdateUserWhenThereIsNoAuthentication() throws Exception {
        //given
        UserDTO userDTO = UserBuilder.buildUserDTO();
        String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
        //providing knowledge
        Mockito.when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);
        //when
        MvcResult result = mvc.perform(
                        put(USERS_API_BASE_URL)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(jsonUserDTO)
                                .contentType(APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isForbidden()).andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_ADMIN })
    void shouldDeleteUserForRoleAdmin() throws Exception {
        //when
        mvc.perform(delete(USERS_API_BASE_URL + "/id"))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USERNAME, authorities = { ROLE_USER })
    void shouldNotDeleteUserForRoleAdmin() throws Exception {
        //when
        mvc.perform(delete(USERS_API_BASE_URL + "/id"))
                .andDo(print())
                //then
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotDeleteUserWhenThereIsNoAuthentication() throws Exception {
        //when
        mvc.perform(delete(USERS_API_BASE_URL + "/id"))
                .andDo(print())
                //then
                .andExpect(status().isForbidden());
    }
}