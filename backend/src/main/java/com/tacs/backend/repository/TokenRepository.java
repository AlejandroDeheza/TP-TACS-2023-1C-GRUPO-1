package com.tacs.backend.repository;

import com.tacs.backend.model.Token;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    List<Token> findAllValidTokenByUsername(String username);
    Optional<Token> findByToken(String token);
    Token save(Token token);
    List<Token> saveAll(List<Token> tokens);
}
