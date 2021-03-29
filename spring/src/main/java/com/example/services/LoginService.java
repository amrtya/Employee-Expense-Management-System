package com.example.services;

import com.example.models.LoginModel;
import com.example.models.ResponseModel;
import com.example.models.ResponseModelSinglePayload;
import com.example.models.UserModel;
import com.example.repositories.LoginModelRepository;
import com.example.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    /**
     * This Service class is used to provide the responses to login requests
     */

    private final LoginModelRepository loginModelRepository;
    private final UserModelRepository userModelRepository;

    @Autowired
    public LoginService(LoginModelRepository loginModelRepository, UserModelRepository userModelRepository) {
        this.loginModelRepository = loginModelRepository;
        this.userModelRepository = userModelRepository;
    }

    public ResponseModelSinglePayload<UserModel> checkUser(LoginModel loginModel) {

        // Find the user email and password by email
        Optional<LoginModel> byId = loginModelRepository.findByEmail(loginModel.getEmail());

        // If user is present, return login successful
        if (byId.isPresent()) {
            if (byId.get().getPassword().equals(loginModel.getPassword())) {

                return new ResponseModelSinglePayload<UserModel>(ResponseModel.SUCCESS, "Logged In Successfully", userModelRepository.findUserByEmail(loginModel.getEmail()).get());
            }
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Incorrect Password", null);
        }

        // Else return failure
        return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Email not found", null);
    }
}
