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
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Test
    public void getAllExpensesTest() throws Exception {

        String endpoint = "/expense";
        String userIdHeader = "userId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"User not found\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "MANAGER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        List<ExpenseModel> expenseModelList = List.of(new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.now(), "REIMBURSED", "someremark", userModel));
        ResponseModelListPayload<ExpenseModel> response = new ResponseModelListPayload<>(ResponseModel.SUCCESS, expenseModelList);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.getAllExpenses(userModel))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"results\":[{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"USER\"}}]}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void getUserDashboardTest() throws Exception {
        String endpoint = "/expense/dashboard/JANUARY";
        String userIdHeader = "userId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"User not found\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "MANAGER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        UserDashboardModel userDashboardModel = new UserDashboardModel(10, 10, 10, 10, "JANUARY");
        ResponseModelSinglePayload<UserDashboardModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userDashboardModel);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.getUserDashboardModel("JANUARY", userModel))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"totalExpense\":10,\"pendingExpense\":10,\"approvedExpense\":10,\"numberExpenses\":10,\"month\":\"JANUARY\"}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void getExpenseTest() throws Exception {
        String endpoint = "/expense/someexpense";
        String userIdHeader = "userId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"User not found\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "MANAGER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        ExpenseModel expenseModel = new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.now(), "REIMBURSED", "someremark", userModel);
        ResponseModelSinglePayload<ExpenseModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.getExpense(expenseModel.getExpenseId()))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"USER\"}}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void addExpenseTest() throws Exception {
        String endpoint = "/expense";
        String userIdHeader = "userId";

        String requestBody = "{\n" +
                "    \"billNumber\": 98744,\n" +
                "    \"billCost\": 125,\n" +
                "    \"datedOn\": \"2000-12-15\",\n" +
                "    \"status\": \"REIMBURSED\",\n" +
                "    \"remark\": \"of course\"\n" +
                "}";
        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"User not found\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "MANAGER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        ExpenseModel expenseModel = new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.now(), "REIMBURSED", "someremark", userModel);
        ResponseModelSinglePayload<ExpenseModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.addExpense(Mockito.any(ExpenseModel.class), Mockito.any(UserModel.class)))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"USER\"}}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateExpenseTest() throws Exception {
        String endpoint = "/expense/someexpense";
        String userIdHeader = "userId";

        String requestBody = "{\n" +
                "    \"billNumber\": 98744,\n" +
                "    \"billCost\": 125,\n" +
                "    \"datedOn\": \"2000-12-15\",\n" +
                "    \"status\": \"REIMBURSED\",\n" +
                "    \"remark\": \"of course\"\n" +
                "}";
        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"User not found\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "MANAGER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, "USER");

        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, "USER");
        ExpenseModel expenseModel = new ExpenseModel("someexpense", 123456L, 10, "image", LocalDate.now(), "REIMBURSED", "someremark", userModel);
        ResponseModelSinglePayload<ExpenseModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, expenseModel);
        Mockito
                .when(expenseService.getUserById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(expenseService.updateExpense(Mockito.anyString(), Mockito.any(ExpenseModel.class), Mockito.anyBoolean()))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"expenseId\":\"someexpense\",\"billNumber\":123456,\"billCost\":10,\"receiptImage\":\"image\",\"datedOn\":\"2021-04-03\",\"status\":\"REIMBURSED\",\"remark\":\"someremark\",\"claimedBy\":{\"userId\":\"someuser\",\"email\":\"myemail@mail.com\",\"username\":\"myname\",\"mobileNumber\":\"13241\",\"active\":true,\"role\":\"USER\"}}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }
}
