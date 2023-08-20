package com.example.card_user.repository;

import com.example.card_user.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> findByImageIdAndDeletedAtIsNull(Integer imageId);
    Optional<Image> findByUserIdAndDeletedAtIsNull(Integer userId);
}
