package com.example.services;

import com.example.models.*;
import com.example.repositories.ExpenseModelRepository;
import com.example.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
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

    public Optional<UserModel> getUserById(String userId) {
        return userModelRepository.findById(userId);
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

    public ResponseModelSinglePayload<ExpenseModel> getExpense(String expenseId) {
        // Get the Expense Model by expenseId
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense not found", null);

        return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.SUCCESS, expenseById.get());
    }

    @Transactional
    public ResponseModelSinglePayload<ExpenseModel> updateExpense(
            String expenseId,
            ExpenseModel expenseModelToUpdate
    ) {
        //Find the expense model by Id
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);

        //If expense model is not found, return failure
        if (expenseById.isEmpty()) {
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense Not found", null);
        }

        ExpenseModel expenseModel = expenseById.get();

        if (expenseModelToUpdate.getBillNumber()!=null && !Objects.equals(expenseModel.getBillNumber(), expenseModelToUpdate.getBillNumber())) {
            expenseModel.setBillNumber(expenseModelToUpdate.getBillNumber());
        }
        if (expenseModelToUpdate.getBillCost()!=null && !Objects.equals(expenseModel.getBillCost(), expenseModelToUpdate.getBillCost())) {
            expenseModel.setBillCost(expenseModelToUpdate.getBillCost());
        }
        if (expenseModelToUpdate.getDatedOn()!=null && !Objects.equals(expenseModel.getDatedOn(), expenseModelToUpdate.getDatedOn())) {
            expenseModel.setDatedOn(expenseModelToUpdate.getDatedOn());
        }
        if (expenseModelToUpdate.getStatus()!=null && !Objects.equals(expenseModel.getStatus(), expenseModelToUpdate.getStatus())) {
            expenseModel.setStatus(expenseModelToUpdate.getStatus());
        }
        if (expenseModelToUpdate.getRemark()!=null && !Objects.equals(expenseModel.getRemark(), expenseModelToUpdate.getRemark())) {
            expenseModel.setRemark(expenseModelToUpdate.getRemark());
        }

        // Return Successful with the updated expenses
        return new ResponseModelSinglePayload<ExpenseModel>(
                ResponseModel.SUCCESS, "Expense Updated Successfully",
                expenseModel
        );

    }

    public ResponseModel deleteExpenseById(String expenseId) {

        // Check if expense exists
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Expense Not found");

        // If expense exists, delete it and return success message
        expenseModelRepository.deleteById(expenseId);
        return new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
    }
}
