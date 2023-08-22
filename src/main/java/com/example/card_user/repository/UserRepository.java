package com.example.card_user.repository;

import com.example.card_user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{

    @Query(
            value = "select * from users where email ilike concat('%',:email,'%') and deleted_at is null",
            nativeQuery = true
    )
    List<User> findByEmail(String email);




    @Query(
            value = "select * from users where first_name ilike concat(:value,'%') and deleted_at is null",
            countQuery = "select count(*) from users",
            nativeQuery = true
    )
    Page<User> findByPageOnOrderBy(Pageable pageable,@Param(value = "value") String value);



    @Query(value = "select * from users where users.id = ? and users.deleted_at is null",
            nativeQuery = true)
    Optional<User> findByUserId(@Param(value = "?") Integer id);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);
}
