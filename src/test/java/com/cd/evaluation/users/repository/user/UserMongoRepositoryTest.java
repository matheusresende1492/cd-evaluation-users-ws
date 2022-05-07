package com.cd.evaluation.users.repository.user;

import com.cd.evaluation.users.builders.user.UserBuilder;
import com.cd.evaluation.users.model.user.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
class UserMongoRepositoryTest {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @AfterEach
    void cleanUp(){
        this.userMongoRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfUserEmailExists() {
        //given
        UserModel userModelToSave = UserBuilder.buildUserModel();
        saveUser(userModelToSave);

        //when
        boolean exists = userMongoRepository.findByEmail(userModelToSave.getEmail()).isPresent();

        //then
        Assertions.assertTrue(exists);
    }

    @Test
    void itShouldCheckIfUserEmailDoesNotExists() {
        //given
        UserModel userModelToSave = UserBuilder.buildUserModel();

        //when
        boolean exists = userMongoRepository.findByEmail(userModelToSave.getEmail()).isPresent();

        //then
        Assertions.assertFalse(exists);
    }

    private void saveUser(UserModel userModel){
        userMongoRepository.save(userModel);
    }
}