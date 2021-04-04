package com.example.services;

import com.example.models.*;
import com.example.repositories.ExpenseModelRepository;
import com.example.repositories.UserModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private UserModelRepository userModelRepository;

    @Mock
    private ExpenseModelRepository expenseModelRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    public void addExpense() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, UserModel.USER);
        ExpenseModel expenseModel = new ExpenseModel("someexpenseid", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);
        List<ExpenseModel> expenseModelList = List.of(new ExpenseModel("someexpenseid", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel));

        ResponseModelSinglePayload<ExpenseModel> expected = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);

        Mockito.when(expenseModelRepository.findAllExpensesByMonth(expenseModel.getDatedOn().getMonth().getValue(), userModel)).thenReturn(expenseModelList);
        Mockito.when(expenseModelRepository.save(expenseModel)).thenReturn(Mockito.mock(ExpenseModel.class));

        ResponseModelSinglePayload<ExpenseModel> response = expenseService.addExpense(expenseModel, userModel);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getResult(), response.getResult());

        expenseModelList = List.of(new ExpenseModel("someexpenseid", 123L, 4900, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel));

        expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Expense Not added as it is exceeding limit: Rs. 5000", null);

        Mockito.when(expenseModelRepository.findAllExpensesByMonth(expenseModel.getDatedOn().getMonth().getValue(), userModel)).thenReturn(expenseModelList);

        response = expenseService.addExpense(expenseModel, userModel);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());

    }

    @Test
    void getExpense() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, UserModel.USER);
        ExpenseModel expenseModel = new ExpenseModel("someexpenseid", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);

        ResponseModelSinglePayload<ExpenseModel> expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Expense not found", null);

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.empty());

        ResponseModelSinglePayload<ExpenseModel> response = expenseService.getExpense(expenseModel.getExpenseId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());

        expected = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.of(expenseModel));

        response = expenseService.getExpense(expenseModel.getExpenseId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getResult(), response.getResult());

    }

    @Test
    void updateExpense() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, UserModel.USER);
        ExpenseModel expenseModel = new ExpenseModel("someexpenseid", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);
        ExpenseModel expenseModelToUpdate = new ExpenseModel("someexpenseid", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);
        List<ExpenseModel> expenseModelList = List.of(new ExpenseModel("someexpenseid1", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel));

        ResponseModelSinglePayload<ExpenseModel> expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Expense Not found", null);

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.empty());

        ResponseModelSinglePayload<ExpenseModel> response = expenseService.updateExpense(expenseModel.getExpenseId(), expenseModelToUpdate, false);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());



        expenseModelToUpdate = new ExpenseModel("someexpenseid", 123L, 4900, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);

        expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Expense Not added as it is exceeding limit: Rs. 5000", null);

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.of(expenseModel));
        Mockito.when(expenseModelRepository.findAllExpensesByMonth(expenseModel.getDatedOn().getMonth().getValue(), userModel)).thenReturn(expenseModelList);

        response = expenseService.updateExpense(expenseModel.getExpenseId(), expenseModelToUpdate, false);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());


        expenseModelToUpdate = new ExpenseModel("someexpenseid", 123L, 1320, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);

        expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "This expense has already been Reimbursed. You cannot change the cost.", null);

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.of(expenseModel));
        Mockito.when(expenseModelRepository.findAllExpensesByMonth(expenseModel.getDatedOn().getMonth().getValue(), userModel)).thenReturn(expenseModelList);

        response = expenseService.updateExpense(expenseModel.getExpenseId(), expenseModelToUpdate, true);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());
    }

    @Test
    void deleteExpenseById() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, UserModel.USER);
        ExpenseModel expenseModel = new ExpenseModel("someexpenseid", 123L, 123, "image", LocalDate.now(), ExpenseModel.REIMBURSED, "someremark", userModel);

        ResponseModel expected = new ResponseModel(ResponseModel.FAILURE, "Expense Not found");

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.empty());

        ResponseModel response = expenseService.deleteExpenseById(expenseModel.getExpenseId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());



        expected = new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");

        Mockito.when(expenseService.getExpenseById(Mockito.anyString())).thenReturn(Optional.of(expenseModel));

        response = expenseService.deleteExpenseById(expenseModel.getExpenseId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
    }

}