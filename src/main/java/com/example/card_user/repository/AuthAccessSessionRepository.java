package com.example.card_user.repository;

import com.example.card_user.model.AuthAccessSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthAccessSessionRepository extends CrudRepository<AuthAccessSession,String> {

}
