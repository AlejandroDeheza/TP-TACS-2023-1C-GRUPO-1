package com.tacs.backend.dao;

import com.tacs.backend.model.Token;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository {

    List<Token> findAllValidTokenByUsername(String username);
    Optional<Token> findByToken(String token);
}
