package com.example.repositories;

import com.example.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, String> {
    /**
     * This Repository handles all the updates to the User Model
     */

    @Query("SELECT u FROM UserModel u WHERE u.email=?1")
    Optional<UserModel> findUserByEmail(String email);
}
