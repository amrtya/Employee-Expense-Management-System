package com.example.services;

import com.example.models.*;
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

@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {

    @Mock
    private UserModelRepository userModelRepository;
    @Mock
    private LoginModelRepository loginModelRepository;

    @InjectMocks
    private SignupService signupService;

    @Test
    public void saveUser() {
        UserReceiver userReceiver = new UserReceiver("raj@mail.com", "raj", "1234567890", true, UserModel.USER, "somepassword");
        UserModel userModel = new UserModel();

        ResponseModelSinglePayload<UserModel> expected = new ResponseModelSinglePayload<>(ResponseModel.EMAIL_TAKEN, "This email is already taken. Try another one.", null);

        Mockito.when(userModelRepository.findUserByEmail(userReceiver.getEmail())).thenReturn(Optional.of(userModel));

        ResponseModelSinglePayload<UserModel> response = signupService.saveUser(userReceiver);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());


        expected = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, "User Added Successfully", userReceiver.getUserModel());

        Mockito.when(userModelRepository.findUserByEmail(userReceiver.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userModelRepository.save(Mockito.any(UserModel.class))).thenReturn(userModel);
        Mockito.when(loginModelRepository.save(Mockito.any(LoginModel.class))).thenReturn(Mockito.any(LoginModel.class));

        response = signupService.saveUser(userReceiver);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
    }
}