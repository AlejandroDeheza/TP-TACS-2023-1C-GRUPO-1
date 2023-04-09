package com.tacs.backend.dao;

import com.tacs.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    boolean exists(String username);
    Optional<User> findUserByUsername(String username);

    User save(User user);
}
