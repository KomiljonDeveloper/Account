package com.example.card_user.repository;

import com.example.card_user.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Integer> {
    Optional<Auth> findByAuthIdAndDeletedAtIsNull(Integer id);
}
