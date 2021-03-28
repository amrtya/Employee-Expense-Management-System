package com.example.controllers;

import com.example.models.ExpenseModel;
import com.example.models.ResponseModel;
import com.example.models.ResponseModelListPayload;
import com.example.models.ResponseModelSinglePayload;
import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        return expenseService.getAllExpenses(userEmail);
    }

    @GetMapping(path = "{id}")
    public ResponseModelSinglePayload<ExpenseModel> getExpense(@PathVariable("id") String expenseId) {
        return expenseService.getExpense(expenseId);
    }

    @PostMapping
    public ResponseModel addExpense(@RequestBody ExpenseModel expenseModel, @RequestParam("userEmail") String userEmail) {
        return expenseService.addExpense(expenseModel, userEmail);
    }

    @PutMapping(path = "{id}")
    public ResponseModelSinglePayload<ExpenseModel> updateExpense(
            @PathVariable("id") String expenseId,
            @RequestBody ExpenseModel expenseModelToUpdate
    ) {
        return expenseService.updateExpense(expenseId, expenseModelToUpdate);
    }
}
