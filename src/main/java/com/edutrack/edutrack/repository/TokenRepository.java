package com.edutrack.edutrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edutrack.edutrack.model.Token;
import com.edutrack.edutrack.model.User;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser(User user);
    Optional<Token> findByToken(String token);

    @Query(value = """
            select t from Token t inner join User u on t.user.id = u.id where u.id = :userId and t.expired = false and t.revoked = false
            """)
    List<Token> findAllValidTokenByUser(Long userId);
}
