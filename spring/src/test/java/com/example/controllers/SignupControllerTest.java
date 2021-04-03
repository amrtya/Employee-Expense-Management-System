package com.example.controllers;

import com.example.models.ResponseModel;
import com.example.models.ResponseModelSinglePayload;
import com.example.models.UserModel;
import com.example.models.UserReceiver;
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

import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SignUpController.class)
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignupService signupService;

    @Test
    public void saveUserTest() throws Exception {
        String requestBody="{\n" +
                "    \"email\": \"some@mail.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"username\": \"someusername\",\n" +
                "    \"mobileNumber\": 6466666546,\n" +
                "    \"active\": true,\n" +
                "    \"role\": \"USER\"\n" +
                "}";
        UserModel userModelResponse = new UserModel("raj", "some@mail.com","someusername","6466666546", true,"USER");
        ResponseModelSinglePayload<UserModel> response = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userModelResponse);

        Mockito
                .when(signupService.saveUser(Mockito.any(UserReceiver.class)))
                .thenReturn(response);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup").content(requestBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"responseType\":\"SUCCESS\",\"message\":\"\",\"result\":{\"userId\":\"raj\",\"email\":\"some@mail.com\",\"username\":\"someusername\",\"mobileNumber\":\"6466666546\",\"active\":true,\"role\":\"USER\"}}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }
}
