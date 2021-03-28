package com.example.services;

import com.example.models.*;
import com.example.repositories.ExpenseModelRepository;
import com.example.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseService {

    /**
     * This Service class is used by the User endpoints and Manager endpoints
     */

    private final UserModelRepository userModelRepository;
    private final ExpenseModelRepository expenseModelRepository;

    @Autowired
    public ExpenseService(UserModelRepository userModelRepository, ExpenseModelRepository expenseModelRepository) {
        this.userModelRepository = userModelRepository;
        this.expenseModelRepository = expenseModelRepository;
    }

    public Optional<UserModel> getUserByEmail(String userEmail) {
        return userModelRepository.findUserByEmail(userEmail);
    }

    public Optional<ExpenseModel> getExpenseById(String expenseId) {
        return expenseModelRepository.findById(expenseId);
    }
    public ResponseModelListPayload<ExpenseModel> getAllExpenses() {
        return new ResponseModelListPayload<ExpenseModel>(ResponseModel.SUCCESS, expenseModelRepository.findAll());
    }

    public ResponseModelListPayload<ExpenseModel> getAllExpenses(UserModel userModel) {

        return new ResponseModelListPayload<ExpenseModel>(
                ResponseModel.SUCCESS,
                expenseModelRepository.findAllExpensesByUser(userModel)
        );
    }

    public ResponseModel addExpense(ExpenseModel expenseModel, UserModel userModel) {

        expenseModel.setClaimedBy(userModel);
        expenseModelRepository.save(expenseModel);
        return new ResponseModel(ResponseModel.SUCCESS, "Expense Added");
    }

    public ResponseModelSinglePayload<ExpenseModel> getExpense(ExpenseModel expenseModel) {

        return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.SUCCESS, expenseModel);
    }

    @Transactional
    public ResponseModelSinglePayload<ExpenseModel> updateExpense(
            ExpenseModel expenseModelCurrent,
            ExpenseModel expenseModelToUpdate
    ) {

        ExpenseModel expenseModel = expenseModelCurrent;

        if (!Objects.equals(expenseModel.getBillNumber(), expenseModelToUpdate.getBillNumber())) {
            expenseModel.setBillNumber(expenseModelToUpdate.getBillNumber());
        }
        if (!Objects.equals(expenseModel.getBillCost(), expenseModelToUpdate.getBillCost())) {
            expenseModel.setBillCost(expenseModelToUpdate.getBillCost());
        }
        if (!Objects.equals(expenseModel.getDatedOn(), expenseModelToUpdate.getDatedOn())) {
            expenseModel.setDatedOn(expenseModelToUpdate.getDatedOn());
        }
        if (!Objects.equals(expenseModel.getStatus(), expenseModelToUpdate.getStatus())) {
            expenseModel.setStatus(expenseModelToUpdate.getStatus());
        }
        if (!Objects.equals(expenseModel.getRemark(), expenseModelToUpdate.getRemark())) {
            expenseModel.setRemark(expenseModelToUpdate.getRemark());
        }

        // Return Successful with the updated expenses
        return new ResponseModelSinglePayload<ExpenseModel>(
                ResponseModel.SUCCESS, "Expense Updated Successfully",
                expenseModel
        );

    }

    public ResponseModel deleteExpenseById(ExpenseModel expenseModel) {


        // If expense exists, delete it and return success message
        expenseModelRepository.delete(expenseModel);
        return new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
    }
}
