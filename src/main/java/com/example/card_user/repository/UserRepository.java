package com.example.card_user.repository;

import com.example.card_user.dto.UserDto;
import com.example.card_user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    Optional<User> findByIdAndDeletedAtIsNull(Integer id);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);
}
