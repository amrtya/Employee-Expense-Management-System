package com.example.services;

import com.example.models.*;
import com.example.repositories.ExpenseModelRepository;
import com.example.repositories.LoginModelRepository;
import com.example.repositories.UserModelRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserModelRepository userModelRepository;
    @Mock
    private LoginModelRepository loginModelRepository;
    @Mock
    private ExpenseModelRepository expenseModelRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void getUser() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, "USER");
        LoginModel loginModel = new LoginModel("someloginid", "rajtilak@mail.com", "somepassword");

        ResponseModelSinglePayload<UserReceiverWithId> expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "User not found.", null);

        Mockito.when(userModelRepository.findById("raj")).thenReturn(Optional.empty());

        ResponseModelSinglePayload<UserReceiverWithId> response = adminService.getUser(userModel.getUserId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());

        UserReceiverWithId userReceiverWithId = new UserReceiverWithId(
                userModel.getUserId(),
                userModel.getEmail(),
                userModel.getUsername(),
                userModel.getMobileNumber(),
                userModel.isActive(),
                userModel.getRole(),
                loginModel.getPassword()
        );
        expected = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, userReceiverWithId);

        Mockito.when(userModelRepository.findById("raj")).thenReturn(Optional.of(userModel));
        Mockito.when(loginModelRepository.findByEmail("rajtilak@mail.com")).thenReturn(Optional.of(loginModel));

        response = adminService.getUser(userModel.getUserId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getResult().getUserId(), response.getResult().getUserId());
        Assertions.assertEquals(expected.getResult().getPassword(), response.getResult().getPassword());
        Assertions.assertEquals(expected.getResult().getUsername(), response.getResult().getUsername());
        Assertions.assertEquals(expected.getResult().isActive(), response.getResult().isActive());
        Assertions.assertEquals(expected.getResult().getRole(), response.getResult().getRole());

    }

    @Test
    void deleteUserById() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, "USER");

        ResponseModel expected = new ResponseModel(ResponseModel.FAILURE, "User not found.");

        Mockito.when(userModelRepository.findById("raj")).thenReturn(Optional.empty());

        ResponseModel response = adminService.deleteUserById(userModel.getUserId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());

        expected = new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");

        Mockito.when(userModelRepository.findById("raj")).thenReturn(Optional.of(userModel));
        Mockito.doNothing().doThrow(new RuntimeException()).when(expenseModelRepository).deleteAllByUserId(Mockito.any(UserModel.class));
        Mockito.doNothing().doThrow(new RuntimeException()).when(loginModelRepository).deleteByEmail(Mockito.anyString());
        Mockito.doNothing().doThrow(new RuntimeException()).when(userModelRepository).delete(Mockito.any(UserModel.class));

        response = adminService.deleteUserById(userModel.getUserId());

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
    }

    @Test
    void updateUser() {
        UserModel userModel = new UserModel("raj", "rajtilak@mail.com", "rajtilak", "1234567890", true, "USER");
        UserReceiver userModelToUpdate = new UserReceiver( "rajtilak2@mail.com", "rajtilak2", "1234567890", true, "MANAGER", "somepassword");

        ResponseModelSinglePayload<UserModel> expected = new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "User not found.", null);

        Mockito.when(userModelRepository.findById(userModel.getUserId())).thenReturn(Optional.empty());

        ResponseModelSinglePayload<UserModel> response = adminService.updateUser(userModel.getUserId(), userModelToUpdate);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());


        LoginModel loginModel = new LoginModel("someloginid", "rajtilak@mail.com", "somepassword");

        expected = new ResponseModelSinglePayload<>(ResponseModel.EMAIL_TAKEN, "This email has already been taken.", null);

        Mockito.when(userModelRepository.findById(userModel.getUserId())).thenReturn(Optional.of(userModel));
        Mockito.when(loginModelRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(loginModel));
        Mockito.when(userModelRepository.findUserByEmail(userModelToUpdate.getEmail())).thenReturn(Optional.of(userModel));

        response = adminService.updateUser(userModel.getUserId(), userModelToUpdate);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult(), response.getResult());


        UserModel userModelUpdated = new UserModel("raj", "rajtilak2@mail.com", "rajtilak2", "1234567890", true, "MANAGER");

        expected = new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, "User Updated Successfully", userModelUpdated);

        Mockito.when(userModelRepository.findById(userModel.getUserId())).thenReturn(Optional.of(userModel));
        Mockito.when(loginModelRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(loginModel));
        Mockito.when(userModelRepository.findUserByEmail(userModelToUpdate.getEmail())).thenReturn(Optional.empty());

        response = adminService.updateUser(userModel.getUserId(), userModelToUpdate);

        Assertions.assertEquals(expected.getResponseType(), response.getResponseType());
        Assertions.assertEquals(expected.getMessage(), response.getMessage());
        Assertions.assertEquals(expected.getResult().getEmail(), response.getResult().getEmail());
        Assertions.assertEquals(expected.getResult().getUsername(), response.getResult().getUsername());
        Assertions.assertEquals(expected.getResult().getRole(), response.getResult().getRole());

    }
}