package com.example.controllers;

import com.example.models.*;
import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseModelListPayload<ExpenseModel> getAllExpenses(@RequestHeader("user_id") String userId) {

        //Check user validity
        Optional<UserModel> userById = expenseService.getUserById(userId);
        if (userById.isEmpty())
            return new ResponseModelListPayload<>(ResponseModel.FAILURE, "User not found", null);

        // Check role validity
        if(!userById.get().getRole().equals(UserModel.USER))
            return new ResponseModelListPayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        // If everything is valid, get expenses
        return expenseService.getAllExpenses(userById.get());
    }

    @GetMapping(path = "dashboard/{month}")
    public ResponseModelSinglePayload<UserDashboardModel> getUserDashboard(@PathVariable("month") String month, @RequestHeader("user_id") String userId)
    {
        //Check user validity
        Optional<UserModel> userById = expenseService.getUserById(userId);
        if (userById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "User not found", null);

        // Check role validity
        if(!userById.get().getRole().equals(UserModel.USER))
            return new ResponseModelSinglePayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        return expenseService.getUserDashboardModel(month, userById.get());
    }

    @GetMapping(path = "{expense_id}")
    public ResponseModelSinglePayload<ExpenseModel> getExpense(@PathVariable("expense_id") String expenseId, @RequestHeader("user_id") String userId) {

        //Check user validity
        Optional<UserModel> userById = expenseService.getUserById(userId);
        if (userById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "User not found", null);

        // Check role validity
        if(!userById.get().getRole().equals(UserModel.USER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        // If everything is valid, get expense
        return expenseService.getExpense(expenseId);
    }

    @PostMapping
    public ResponseModelSinglePayload<ExpenseModel> addExpense(@RequestBody ExpenseModel expenseModel, @RequestHeader("user_id") String userId) {

        //Check user validity
        Optional<UserModel> userById = expenseService.getUserById(userId);
        if (userById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "User not found", null);

        // Check role validity
        if(!userById.get().getRole().equals(UserModel.USER))
            return new ResponseModelSinglePayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again.", null);

        // If everything is valid, add expense
        return expenseService.addExpense(expenseModel, userById.get());
    }

    @PutMapping(path = "{expense_id}")
    public ResponseModelSinglePayload<ExpenseModel> updateExpense(
            @PathVariable("expense_id") String expenseId,
            @RequestBody ExpenseModel expenseModelToUpdate,
            @RequestHeader("user_id") String userId
    ) {

        //Check user validity
        Optional<UserModel> userById = expenseService.getUserById(userId);
        if (userById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "User not found", null);

        // Check role validity
        if(!userById.get().getRole().equals(UserModel.USER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);


        return expenseService.updateExpense(expenseId, expenseModelToUpdate);
    }
}
