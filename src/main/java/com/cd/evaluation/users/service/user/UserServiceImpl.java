package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.commons.converter.user.UserDTOConverter;
import com.cd.evaluation.users.commons.converter.user.UserSearchDTOConverter;
import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import com.cd.evaluation.users.view.users.UserDTO;
import com.cd.evaluation.users.view.users.search.UserSearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
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

    private final UserSearchDTOConverter userSearchDTOConverter;

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
        if (Objects.isNull(userId)) {
            log.error("User ID cannot be null.");
            throw new InternalException(InternalException.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
        }
        UserModel userFound = userMongoRepository.findById(userId).stream().findFirst().orElse(null);
        log.info("User found in the database: {}", userFound);
        return Objects.isNull(userFound) ? null : userDTOConverter.convertToDTO(userFound);
    }

    /**
     * Function to retrieve the ALL user from the database
     */
    @Override
    public Page<UserModel> getAllUsers(int page, int pageSize, String field, String sortDirection, UserSearchDTO userSearchDTO) throws InternalException {
        Page<UserModel> dataToReturn;
        Pageable pageable = StringUtils.isBlank(field)
                ? PageRequest.of(page * pageSize, pageSize)
                : PageRequest.of(page * pageSize, pageSize).withSort(Sort.by(Sort.Direction.fromString(sortDirection), field));
        //Its necessary the null check because of the search conversion into model
        if (Objects.isNull(userSearchDTO)) {
            dataToReturn = userMongoRepository.findAll(pageable);
        } else {
            UserModel userModelForSearch = userSearchDTOConverter.convertToModelFromSearch(userSearchDTO);
            Example<UserModel> userModelExample = Example.of(userModelForSearch, ExampleMatcher.matchingAll().withIgnoreCase());
            dataToReturn = userMongoRepository.findAll(userModelExample, pageable);
        }
        //dataToReturn is never null because of the page interface
        //Removing all passwords for security reasons
        dataToReturn.getContent().forEach(userModel -> userModel.setPassword(null));
        log.info("Retrieved users: {}", dataToReturn.getContent());
        return dataToReturn;
    }

    /**
     * Function to save the user in the database
     * @param userDTO user payload
     */
    @Override
    public UserDTO saveUser(UserDTO userDTO) throws InternalException {
        if (Objects.isNull(userDTO)) {
            log.error("User payload cannot be null.");
            throw new InternalException(InternalException.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
        }
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
        if (Objects.isNull(userDTO)) {
            log.error("User payload cannot be null.");
            throw new InternalException(InternalException.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
        }
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
        if (Objects.isNull(userId)) {
            log.error("User ID cannot be null.");
            throw new InternalException(InternalException.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
        }
        log.info("Deleting user with ID: {}", userId);
        userMongoRepository.deleteById(userId);
    }
}
