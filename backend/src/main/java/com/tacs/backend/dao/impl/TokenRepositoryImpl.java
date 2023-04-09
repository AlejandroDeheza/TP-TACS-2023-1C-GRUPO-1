package com.tacs.backend.dao.impl;

import com.tacs.backend.dao.TokenRepository;
import com.tacs.backend.model.Token;
import com.tacs.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class TokenRepositoryImpl implements TokenRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Token> findAllValidTokenByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.find(query, Token.class);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        Query query = new Query(Criteria.where("token").is(token));
        return Optional.ofNullable(mongoTemplate.findOne(query, Token.class));
    }
}
