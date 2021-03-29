package com.example.controllers;

import com.example.models.*;
import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "manager")
public class ManagerController {

    /**
     * This Controller class provides the endpoints for the manager
     */

    private final ExpenseService expenseService;

    @Autowired
    public ManagerController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseModelListPayload<ExpenseModel> getAllExpenses(@RequestHeader("manager_id") String managerId) {

        Optional<UserModel> managerById = expenseService.getUserById(managerId);
        if (managerById.isEmpty())
            return new ResponseModelListPayload<ExpenseModel>(ResponseModel.FAILURE, "Manager not found", null);

        if (!managerById.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModelListPayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        return expenseService.getAllExpenses();
    }

    @GetMapping("/expense/{id}")
    public ResponseModelSinglePayload<ExpenseModel> getExpense(@PathVariable("id") String expenseId, @RequestHeader("manager_id") String managerId) {

        Optional<UserModel> managerById = expenseService.getUserById(managerId);
        if (managerById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Manager not found", null);

        // Check role validity
        if (!managerById.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        // If everything is valid, get expense
        return expenseService.getExpense(expenseId);
    }

    @PutMapping(path = "expense/{expenseId}")
    public ResponseModelSinglePayload<ExpenseModel> updateExpenses(
            @PathVariable("expenseId") String expenseId,
            @RequestBody ExpenseModel expenseModelToUpdate,
            @RequestHeader("manager_id") String managerId
    ) {

        Optional<UserModel> managerById = expenseService.getUserById(managerId);
        if (managerById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Manager not found.", null);

        // Check role validity
        if (!managerById.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        // If everything checks out, update the expense
        return expenseService.updateExpense(expenseId, expenseModelToUpdate);
    }

    @DeleteMapping(path = "/expense/{id}")
    public ResponseModel deleteExpense(@PathVariable("id") String expenseId, @RequestHeader("manager_id") String managerId) {

        Optional<UserModel> managerById = expenseService.getUserById(managerId);
        if (managerById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Manager not found.");

        if (!managerById.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModel(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again.");

        // If everything is valid, delete expense
        return expenseService.deleteExpenseById(expenseId);
    }
}
