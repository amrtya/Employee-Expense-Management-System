package com.example.services;

import com.example.models.LoginModel;
import com.example.models.ResponseModel;
import com.example.models.ResponseModelSinglePayload;
import com.example.models.UserModel;
import com.example.repositories.LoginModelRepository;
import com.example.repositories.UserModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserModelRepository userModelRepository;
    @Mock
    private LoginModelRepository loginModelRepository;

    @InjectMocks
    private LoginService loginService;

    @Test
    public void checkUser() {
        LoginModel loginModel = new LoginModel("someloginid", "raj@mail.com", "password");

        ResponseModelSinglePayload<UserModel> expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Email not found", null);

        Mockito.when(loginModelRepository.findByEmail(loginModel.getEmail())).thenReturn(Optional.empty());

        ResponseModelSinglePayload<UserModel> response = loginService.checkUser(loginModel);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());

        LoginModel loginModelFromUser = new LoginModel("someloginid", "raj@mail.com", "password132");

        expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Incorrect Password", null);

        Mockito.when(loginModelRepository.findByEmail(loginModel.getEmail())).thenReturn(Optional.of(loginModel));

        response = loginService.checkUser(loginModelFromUser);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());


        loginModelFromUser = new LoginModel("someloginid", "raj@mail.com", "password");
        UserModel userModel = new UserModel("raj", "raj@mail.com", "rajtilak", "1234567890", true, "USER");

        expected = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, "Logged In Successfully", userModel);

        Mockito.when(loginModelRepository.findByEmail(loginModel.getEmail())).thenReturn(Optional.of(loginModel));
        Mockito.when(userModelRepository.findUserByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));

        response = loginService.checkUser(loginModelFromUser);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
    }
}