package com.example.controllers;

import com.example.models.*;
import com.example.services.ExpenseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ManagerController.class)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Test
    public void getAllExpensesTest() throws Exception {
        String endpoint = "/manager";
        String userIdHeader = "managerId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Manager not found\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.MANAGER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);
        List<ExpenseModel> expenseModelList = List.of(new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.of(2021, 4, 3), ExpenseModel.REIMBURSED, "someremark", userModel));
        ResponseModelListPayload<ExpenseModel> response = new ResponseModelListPayload<>(ResponseModel.SUCCESS, expenseModelList);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.getAllExpenses())
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"results\":[{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"MANAGER\"}}]}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void getExpenseTest() throws Exception {
        String endpoint = "/manager/expense/someexpense";
        String userIdHeader = "managerId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Manager not found\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.MANAGER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);
        ExpenseModel expenseModel = new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.of(2021, 4, 3), ExpenseModel.REIMBURSED, "someremark", userModel);
        ResponseModelSinglePayload<ExpenseModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.getExpense(expenseModel.getExpenseId()))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"MANAGER\"}}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateExpenseTest() throws Exception {
        String endpoint = "/manager/expense/someexpense";
        String userIdHeader = "managerId";

        String requestBody = "{\n" +
                "    \"billNumber\": 98744,\n" +
                "    \"billCost\": 125,\n" +
                "    \"datedOn\": \"2000-12-15\",\n" +
                "    \"status\": \"REIMBURSED\",\n" +
                "    \"remark\": \"of course\"\n" +
                "}";
        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Manager not found\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.MANAGER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);
        ExpenseModel expenseModel = new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.of(2021, 4, 3), ExpenseModel.REIMBURSED, "someremark", userModel);
        ResponseModelSinglePayload<ExpenseModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.updateExpense(Mockito.anyString(), Mockito.any(ExpenseModel.class), Mockito.anyBoolean()))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"MANAGER\"}}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void deleteExpenseTest() throws Exception {
        String endpoint = "/manager/expense/someexpense";
        String userIdHeader = "managerId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Manager not found\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.MANAGER);

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.MANAGER);
        ResponseModel response = new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.deleteExpenseById("someexpense"))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"Deleted Successfully\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

}
