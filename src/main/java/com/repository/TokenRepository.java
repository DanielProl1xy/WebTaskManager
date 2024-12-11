package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.entity.Token;
import java.util.Optional;


public interface TokenRepository extends CrudRepository<Token, Long> {

    public Optional<Token> findByToken(String token);
}
