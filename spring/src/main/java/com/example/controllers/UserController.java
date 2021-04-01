package com.example.controllers;

import com.example.models.*;
import com.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

        if(!userById.get().isActive())
            return new ResponseModelListPayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);
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

        if(!userById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);

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

        if(!userById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);

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

        if(!userById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);
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

        if(!userById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);

        return expenseService.updateExpense(expenseId, expenseModelToUpdate, true);
    }

    @PostMapping(path = "upload/{expense_id}", consumes="multipart/form-data")
    public ResponseModel uploadReceiptImage(@PathVariable("expense_id") String expenseId, @RequestPart("receipt_image") MultipartFile receiptImage, @RequestHeader("user_id") String userId)
    {
        //Check user validity
        Optional<UserModel> userById = expenseService.getUserById(userId);
        if (userById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "User not found");

        // Check role validity
        if(!userById.get().getRole().equals(UserModel.USER))
            return new ResponseModel(ResponseModel.ROLE_CHANGED, "Your role has changed. Please login again");

        if(!userById.get().isActive())
            return new ResponseModel(ResponseModel.INACTIVE, "Your account is currently suspended.");
        try {
            expenseService.storeReceiptImage(receiptImage, expenseId);
        } catch (IOException e) {
            return new ResponseModel(ResponseModel.FAILURE, "Receipt Image Upload Failed");
        }

        return new ResponseModel(ResponseModel.SUCCESS, "Receipt Image Uploaded Successfully");
    }
}
