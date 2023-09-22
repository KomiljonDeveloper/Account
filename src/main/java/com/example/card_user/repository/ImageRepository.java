package com.example.card_user.repository;

import com.example.card_user.model.Image;
import jakarta.persistence.NamedQuery;
import jdk.dynalink.linker.LinkerServices;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> findByImageIdAndDeletedAtIsNull(Integer imageId);
    Optional<Image> findByUserIdAndDeletedAtIsNull(Integer userId);
    @Query(name = "deleting image")
    List<Image> deletingImage();
}
