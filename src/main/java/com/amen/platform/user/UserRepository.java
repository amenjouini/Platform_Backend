package com.amen.platform.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    List<User> findByBlocked(boolean blocked);

    List<User> findAllByIdNot(String id);


    Optional<User> findByNickname(String nickname);
}
