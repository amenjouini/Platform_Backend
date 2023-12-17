package com.amen.platform.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findByUserIdAndExpiredIsFalseAndRevokedIsFalse(String userId);
    Optional<Token> findByToken(String token);
    List<Token> deleteByUserId(String userId);
}
