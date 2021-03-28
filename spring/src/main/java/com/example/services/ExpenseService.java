package com.example.services;

import com.example.models.*;
import com.example.repositories.ExpenseModelRepository;
import com.example.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseModelListPayload<ExpenseModel> getAllExpenses(String userEmail) {

        //Check email validity
        Optional<UserModel> userByEmail = getUserByEmail(userEmail);
        if (userByEmail.isEmpty())
            return new ResponseModelListPayload<>(ResponseModel.FAILURE, "Email not found", List.of());

        //If email is valid, return the user's expenses using email
        return new ResponseModelListPayload<ExpenseModel>(
                ResponseModel.SUCCESS,
                expenseModelRepository.findAllExpensesByUser(userByEmail.get())
        );
    }

    public ResponseModel addExpense(ExpenseModel expenseModel, String userEmail) {

        //Check email validity
        Optional<UserModel> userByEmail = getUserByEmail(userEmail);
        if (userByEmail.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Email not found");

        //If the email is valid, get the User Model and set it to the Expense model
        expenseModel.setClaimedBy(userByEmail.get());
        expenseModelRepository.save(expenseModel);
        return new ResponseModel(ResponseModel.SUCCESS, "Expense Added");
    }

    public ResponseModelSinglePayload<ExpenseModel> getExpense(String expenseId) {
        // Get the Expense Model by expenseId
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense not found", null);

        // If expense found, return response
        return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.SUCCESS, expenseById.get());
    }

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

        //If expense model is found, update the corresponding fields and respond back the updated details
        ExpenseModel expenseModel = expenseById.get();

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
}
