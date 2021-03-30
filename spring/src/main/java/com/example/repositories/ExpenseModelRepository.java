package com.example.repositories;

import com.example.models.ExpenseModel;
import com.example.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseModelRepository extends JpaRepository<ExpenseModel, String> {

    /**
     * This Repository handles all the updates to the Expense Model
     */

    @Query("SELECT e FROM ExpenseModel e WHERE e.claimedBy=?1")
    List<ExpenseModel> findAllExpensesByUser(UserModel userModel);

    @Query("SELECT e FROM ExpenseModel e WHERE month(e.datedOn)=?1 AND e.claimedBy=?2")
    List<ExpenseModel> findAllExpensesByMonth(int month, UserModel userModel);
}
