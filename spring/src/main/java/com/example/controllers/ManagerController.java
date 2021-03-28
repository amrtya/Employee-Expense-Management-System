package com.example.controllers;

import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "manager")
public class ManagerController {

    private final ExpenseService expenseService;

    @Autowired
    public ManagerController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public void getAllExpenses()
    {

    }

    @GetMapping("/expense/{id}")
    public void getExpense(@PathVariable("id") String expenseId)
    {

    }

//    @PutMapping(path = "expense/{expenseId}")
//    public void updateExpenses(@PathVariable("expenseId") String expenseId)
//    {
//
//    }

    @DeleteMapping(path = "/expense/{id}")
    public void deleteExpense(@PathVariable("id") String expenseId)
    {

    }
}
