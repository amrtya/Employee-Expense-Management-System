package com.example.controllers;

import com.example.models.ExpenseModel;
import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "expense")
public class UserController {

    private final ExpenseService expenseService;

    @Autowired
    public UserController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public void getAllExpenses(@RequestParam("userEmail") String userEmail ) {

    }

    @GetMapping(path = "{id}")
    public void getExpense(@PathVariable("id") String expenseId) {

    }

    @PostMapping
    public void addExpense(ExpenseModel expenseModel, @RequestParam("userEmail") String userEmail)
    {

    }

//    @PutMapping(path = "{id}")
//    public void updateExpense(@PathVariable("id") String expenseId)
//    {
//
//    }
}
