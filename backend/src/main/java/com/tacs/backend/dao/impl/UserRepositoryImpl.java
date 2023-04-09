package com.tacs.backend.dao.impl;

import com.tacs.backend.dao.UserRepository;
import com.tacs.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean exists(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.exists(query, User.class);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    @Override
    public User save(User user) {
        return mongoTemplate.save(user);
    }
}
