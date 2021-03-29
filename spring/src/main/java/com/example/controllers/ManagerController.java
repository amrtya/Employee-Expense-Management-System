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
    public ResponseModelListPayload<ExpenseModel> getAllExpenses(@RequestParam("managerEmail") String managerEmail) {

        Optional<UserModel> managerByEmail = expenseService.getUserByEmail(managerEmail);
        if (managerByEmail.isEmpty())
            return new ResponseModelListPayload<ExpenseModel>(ResponseModel.FAILURE, "Manager does not exist.", null);

        if (!managerByEmail.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModelListPayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        ResponseModelListPayload<ExpenseModel> allExpenses = expenseService.getAllExpenses();

        return allExpenses;
    }

    @GetMapping("/expense/{id}")
    public ResponseModelSinglePayload<ExpenseModel> getExpense(@PathVariable("id") String expenseId, @RequestParam("managerEmail") String managerEmail) {

        Optional<UserModel> managerByEmail = expenseService.getUserByEmail(managerEmail);
        if (managerByEmail.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Manager does not exist.", null);

        // Check role validity
        if (!managerByEmail.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        Optional<ExpenseModel> expenseById = expenseService.getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense not found", null);

        // If everything is valid, get expense
        return expenseService.getExpense(expenseById.get());
    }

    @PutMapping(path = "expense/{expenseId}")
    public ResponseModelSinglePayload<ExpenseModel> updateExpenses(
            @PathVariable("expenseId") String expenseId,
            @RequestBody ExpenseModel expenseModelToUpdate,
            @RequestParam("managerEmail") String managerEmail
    ) {

        Optional<UserModel> managerByEmail = expenseService.getUserByEmail(managerEmail);
        if (managerByEmail.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Manager does not exist.", null);

        // Check role validity
        if (!managerByEmail.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again", null);

        //Find the expense model by Id
        Optional<ExpenseModel> expenseById = expenseService.getExpenseById(expenseId);

        //If expense model is not found, return failure
        if (expenseById.isEmpty()) {
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense Not found", null);
        }

        // If everything checks out, update the expense
        return expenseService.updateExpense(expenseById.get(), expenseModelToUpdate);
    }

    @DeleteMapping(path = "/expense/{id}")
    public ResponseModel deleteExpense(@PathVariable("id") String expenseId, @RequestParam("managerEmail") String managerEmail) {

        Optional<UserModel> managerByEmail = expenseService.getUserByEmail(managerEmail);
        if (managerByEmail.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Manager does not exist.");

        if (!managerByEmail.get().getRole().equals(UserModel.MANAGER))
            return new ResponseModel(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again.");

        // Check if expense exists
        Optional<ExpenseModel> expenseById = expenseService.getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Expense Not found");

        // If everything is valid, delete expense
        return expenseService.deleteExpenseById(expenseById.get());
    }
}
