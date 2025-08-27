package com.gustavosdaniel.stack.authservice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail (String email);

    @Query("""
            SELECT u FROM User u WHERE lower(u.username)
                         LIKE LOWER(CONCAT( '%', :username, '%')) ORDER BY u.createdAt DESC
            """)
    Page<User> findByName (@Param("username") String username, Pageable pageable);

    // verificar se já existe um paciente com um determinado e-mail, excluindo o próprio paciente que está sendo atualizado
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);

}
