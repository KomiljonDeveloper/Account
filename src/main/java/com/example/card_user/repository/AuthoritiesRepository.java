package com.example.card_user.repository;

import com.example.card_user.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Auth,Integer> {
}
