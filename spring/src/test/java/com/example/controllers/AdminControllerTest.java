package com.example.controllers;

import com.example.models.*;
import com.example.services.AdminService;
import com.example.services.SignupService;
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

import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private SignupService signupService;

    @Test
    public void getAllUsersTest() throws Exception {
        String endpoint = "/admin";
        String userIdHeader = "adminId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Admin not found.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"results\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);
        List<UserModel> userModelList = List.of(new UserModel("someotheruserid", "some@mail.com","some","1234567890", true,UserModel.USER));
        ResponseModelListPayload<UserModel> response = new ResponseModelListPayload<>(ResponseModel.SUCCESS, userModelList);
        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(adminService.getAllUsers())
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"results\":[{\"userId\":\"someotheruserid\",\"email\":\"some@mail.com\",\"username\":\"some\",\"mobileNumber\":\"1234567890\",\"active\":true,\"role\":\"USER\"}]}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void getUserTest() throws Exception {
        String endpoint = "/admin/someotheruserid";
        String userIdHeader = "adminId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Admin not found.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);
        UserReceiverWithId userReceiverWithId = new UserReceiverWithId("someotheruserid", "some@mail.com","some","1234567890", true,UserModel.USER, "somepassword");
        ResponseModelSinglePayload<UserReceiverWithId> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userReceiverWithId);
        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(adminService.getUser(userReceiverWithId.getUserId()))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.get(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"email\":\"some@mail.com\",\"username\":\"some\",\"mobileNumber\":\"1234567890\",\"active\":true,\"role\":\"USER\",\"password\":\"somepassword\",\"userId\":\"someotheruserid\",\"userModel\":{\"userId\":null,\"email\":\"some@mail.com\",\"username\":\"some\",\"mobileNumber\":\"1234567890\",\"active\":true,\"role\":\"USER\"}}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void addUserTest() throws Exception {
        String endpoint = "/admin/user";
        String userIdHeader = "adminId";

        String requestBody="{\n" +
                "    \"email\": \"rajtilak@mail.com\",\n" +
                "    \"password\": \"password123\",\n" +
                "    \"username\": \"rajtilak\",\n" +
                "    \"mobileNumber\": 6466666546,\n" +
                "    \"active\": true,\n" +
                "    \"role\": \"USER\"\n" +
                "}";
        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Admin not found.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);
        UserModel userModelResponse = new UserModel("raj", "rajtilak@mail.com","rajtilak","6466666546", true,UserModel.USER);
        ResponseModelSinglePayload<UserModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userModelResponse);
        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(signupService.saveUser(Mockito.any(UserReceiver.class)))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.post(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"userId\":\"raj\",\"email\":\"rajtilak@mail.com\",\"username\":\"rajtilak\",\"mobileNumber\":\"6466666546\",\"active\":true,\"role\":\"USER\"}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateUserTest() throws Exception {
        String endpoint = "/admin/user/raj";
        String userIdHeader = "adminId";

        String requestBody="{\n" +
                "    \"email\": \"rajtilak@mail.com\",\n" +
                "    \"password\": \"password123\",\n" +
                "    \"username\": \"rajtilak\",\n" +
                "    \"mobileNumber\": 6466666546,\n" +
                "    \"active\": true,\n" +
                "    \"role\": \"USER\"\n" +
                "}";
        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Admin not found.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\",\"result\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);
        UserModel userModelResponse = new UserModel("raj", "rajtilak@mail.com","rajtilak","6466666546", true,UserModel.USER);
        ResponseModelSinglePayload<UserModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userModelResponse);
        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(adminService.updateUser(eq("raj"), Mockito.any(UserReceiver.class)))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.put(endpoint).content(requestBody).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"userId\":\"raj\",\"email\":\"rajtilak@mail.com\",\"username\":\"rajtilak\",\"mobileNumber\":\"6466666546\",\"active\":true,\"role\":\"USER\"}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void deleteUserTest() throws Exception {
        String endpoint = "/admin/user/someotheruser";
        String userIdHeader = "adminId";

        //<------------ User not found test ---------------->
        UserModel userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, "someotheruser").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"FAILURE\",\"message\":\"Admin not found.\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<-------------------- Role Changed Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.USER);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"ROLE_CHANGED\",\"message\":\"Your role has changed. Please Login again.\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<--------------------- Suspension Test ----------------->

        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", false, UserModel.ADMIN);

        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"INACTIVE\",\"message\":\"Your account is currently suspended.\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

        //<----------------------- Success Test --------------->
        userModel = new UserModel("someuser", "myemail@mail.com", "myname", "13241", true, UserModel.ADMIN);
        ResponseModel response = new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
        Mockito
                .when(adminService.getAdminById("someuser"))
                .thenReturn(Optional.of(userModel));

        Mockito
                .when(adminService.deleteUserById(eq("someotheruser")))
                .thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.delete(endpoint).header(userIdHeader, userModel.getUserId()).accept(MediaType.APPLICATION_JSON);

        mvcResult = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"responseType\":\"SUCCESS\",\"message\":\"Deleted Successfully\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }
}
