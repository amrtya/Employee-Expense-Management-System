package com.example.controllers;

import com.example.models.*;
import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "expense")
public class UserController {

    /**
     * This Controller class gives the endpoints for the users
     */

    private final ExpenseService expenseService;

    @Autowired
    public UserController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseModelListPayload<ExpenseModel> getAllExpenses(@RequestParam("userEmail") String userEmail) {

        //Check email validity
        Optional<UserModel> userByEmail = expenseService.getUserByEmail(userEmail);
        if (userByEmail.isEmpty())
            return new ResponseModelListPayload<>(ResponseModel.FAILURE, "Email not found", null);

        // Check role validity
        if(!userByEmail.get().getRole().equals(UserModel.USER))
            return new ResponseModelListPayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        // If everything is valid, get expenses
        return expenseService.getAllExpenses(userByEmail.get());
    }

    @GetMapping(path = "{id}")
    public ResponseModelSinglePayload<ExpenseModel> getExpense(@PathVariable("id") String expenseId) {
        // Get the Expense Model by expenseId
        Optional<ExpenseModel> expenseById = expenseService.getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense not found", null);

        // Check role validity
        if(!expenseById.get().getClaimedBy().getRole().equals(UserModel.USER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        // If everything is valid, get expense
        return expenseService.getExpense(expenseById.get());
    }

    @PostMapping
    public ResponseModel addExpense(@RequestBody ExpenseModel expenseModel, @RequestParam("userEmail") String userEmail) {

        //Check email validity
        Optional<UserModel> userByEmail = expenseService.getUserByEmail(userEmail);
        if (userByEmail.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Email not found");

        // Check role validity
        if(!userByEmail.get().getRole().equals(UserModel.USER))
            return new ResponseModel(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again.");

        // If everything is valid, add expense
        return expenseService.addExpense(expenseModel, userByEmail.get());
    }

    @PutMapping(path = "{id}")
    public ResponseModelSinglePayload<ExpenseModel> updateExpense(
            @PathVariable("id") String expenseId,
            @RequestBody ExpenseModel expenseModelToUpdate
    ) {
        //Find the expense model by Id
        Optional<ExpenseModel> expenseById = expenseService.getExpenseById(expenseId);

        //If expense model is not found, return failure
        if (expenseById.isEmpty()) {

            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense Not found", null);
        }

        if(!expenseById.get().getClaimedBy().getRole().equals(UserModel.USER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        return expenseService.updateExpense(expenseById.get(), expenseModelToUpdate);
    }
}
