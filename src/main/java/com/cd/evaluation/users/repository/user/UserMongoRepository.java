package com.cd.evaluation.users.repository.user;

import com.cd.evaluation.users.model.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
}
