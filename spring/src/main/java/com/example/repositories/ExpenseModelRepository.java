package com.example.repositories;

import com.example.models.ExpenseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseModelRepository extends JpaRepository<ExpenseModel, String> {
}
