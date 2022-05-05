package com.cd.evaluation.users.service.user;

import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMongoRepository userMongoRepository;
    private final PasswordEncoder passwordEncoder;

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
        userModel.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(userModel.getEmail(), userModel.getPassword(), authorities);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userMongoRepository.findAll();
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userMongoRepository.save(userModel);
    }
}
