package com.example.controllers;

import com.example.models.*;
import com.example.services.LoginService;
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

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    public void checkUserTest() throws Exception {
        String requestBody="{\n" +
                "    \"email\": \"sample@mail.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        UserModel userModelResponse = new UserModel("raj", "some@mail.com","someusername","6466666546", true,UserModel.USER);
        ResponseModelSinglePayload<UserModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userModelResponse);

        Mockito
                .when(loginService.checkUser(Mockito.any(LoginModel.class)))
                .thenReturn(response);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login").content(requestBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"userId\":\"raj\",\"email\":\"some@mail.com\",\"username\":\"someusername\",\"mobileNumber\":\"6466666546\",\"active\":true,\"role\":\"USER\"}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }
}
