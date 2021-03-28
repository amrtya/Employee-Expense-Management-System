package com.example.repositories;

import com.example.models.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginModelRepository extends JpaRepository<LoginModel, String> {
    /**
     * This Repository handles all the updates to the Login Model
     */
}
