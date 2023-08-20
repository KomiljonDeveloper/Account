package com.example.card_user.repository;

import com.example.card_user.dto.CardDto;
import com.example.card_user.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    Optional<Card> findByCardIdAndDeletedAtIsNull(Integer id);

    List<Card> findAllByUserIdAndDeletedAtIsNull(Integer userId);

    boolean existsByCardNumberAndDeletedAtIsNull(String cardNumber);

    Optional<Card> findByCardNumberAndDeletedAtIsNull(String cardNumber);


}
