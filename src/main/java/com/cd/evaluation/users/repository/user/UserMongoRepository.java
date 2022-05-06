package com.cd.evaluation.users.repository.user;

import com.cd.evaluation.users.model.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MongoDb data access layer
 */
@Repository
public interface UserMongoRepository extends MongoRepository<UserModel, String> {
    //Mongodb function to retrieve a user by the email
    Optional<UserModel> findByEmail(String email);
}
