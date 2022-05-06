package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.commons.converter.user.UserDTOConverter;
import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import com.cd.evaluation.users.view.users.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * User business logic class
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMongoRepository userMongoRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserDTOConverter userDTOConverter;

    /**
     * Function to retrieve the user from the database for the springSecurity
     * @param email username, in this application also the email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userMongoRepository.findByEmail(email).stream().findFirst().orElse(null);
        if (userModel == null){
            log.error("Error retrieving user from mongo database");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found in mongo database {}", userModel);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userModel.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));
        return new org.springframework.security.core.userdetails.User(userModel.getEmail(), userModel.getPassword(), authorities);
    }

    /**
     * Function to retrieve the user from the database
     * @param userId user's ID
     */
    @Override
    public UserDTO getUserById(String userId) throws InternalException {
        UserModel userFound = userMongoRepository.findById(userId).stream().findFirst().orElse(null);
        log.info("User found in the database: {}", userFound);
        return Objects.isNull(userFound) ? null : userDTOConverter.convertToDTO(userFound);
    }

    /**
     * Function to retrieve the ALL user from the database
     */
    @Override
    public List<UserDTO> getAllUsers() throws InternalException {
        return userDTOConverter.convertToDTOList(userMongoRepository.findAll());
    }

    /**
     * Function to save the user in the database
     * @param userDTO user payload
     */
    @Override
    public UserDTO saveUser(UserDTO userDTO) throws InternalException {
        UserModel userModel = userDTOConverter.convertToModel(userDTO);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        log.info("Saving user in the database: {}", userModel);
        return userDTOConverter.convertToDTO(userMongoRepository.save(userModel));
    }

    /**
     * Function to update the user in the database
     * @param userDTO user payload
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO) throws InternalException {
        UserModel userModel = userDTOConverter.convertToModel(userDTO);
        if (Objects.isNull(userModel.getId())) {
            log.error("User payload and ID cannot be null.");
            throw new InternalException(InternalException.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTOFoundById = this.getUserById(userModel.getId());
        if (Objects.isNull(userDTOFoundById)) {
            log.error("Invalid user ID");
            throw new InternalException(InternalException.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
        }
        log.info("User found in the database: {}", userDTOFoundById);
        log.info("Updating user in the database: {}", userModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userDTOConverter.convertToDTO(userMongoRepository.save(userModel));
    }

    /**
     * Function to delete the user from the database
     * @param userId user's ID
     */
    @Override
    public void deleteUser(String userId) throws InternalException {
        log.info("Deleting user with ID: {}", userId);
        userMongoRepository.deleteById(userId);
    }
}
