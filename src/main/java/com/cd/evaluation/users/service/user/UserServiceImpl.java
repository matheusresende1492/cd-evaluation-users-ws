package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.commons.converter.user.UserDTOConverter;
import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import com.cd.evaluation.users.view.users.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMongoRepository userMongoRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserDTOConverter userDTOConverter;

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

    @Override
    public UserDTO getUserById(String userId) throws InternalException {
        UserModel userFound = userMongoRepository.findById(userId).stream().findFirst().orElse(null);
        return Objects.isNull(userFound) ? null : userDTOConverter.convertToDTO(userFound);
    }

    @Override
    public List<UserDTO> getAllUsers() throws InternalException {
        return userDTOConverter.convertToDTOList(userMongoRepository.findAll());
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) throws InternalException {
        UserModel userModel = userDTOConverter.convertToModel(userDTO);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userDTOConverter.convertToDTO(userMongoRepository.save(userModel));
    }

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
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userDTOConverter.convertToDTO(userMongoRepository.save(userModel));
    }

    @Override
    public void deleteUser(String userId) throws InternalException {
        userMongoRepository.deleteById(userId);
    }
}
