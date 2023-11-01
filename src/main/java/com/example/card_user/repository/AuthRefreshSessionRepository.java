package com.example.card_user.repository;

import com.example.card_user.model.AuthRefreshSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRefreshSessionRepository extends CrudRepository<AuthRefreshSession,String> {

}
