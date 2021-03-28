package com.example.services;

import com.example.models.LoginModel;
import com.example.models.ResponseModel;
import com.example.repositories.LoginModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    /**
     * This Service class is used to provide the responses to login requests
     */

    private final LoginModelRepository loginModelRepository;

    @Autowired
    public LoginService(LoginModelRepository loginModelRepository) {
        this.loginModelRepository = loginModelRepository;
    }

    public ResponseModel checkUser(LoginModel loginModel) {

        // Find the user email and password by email
        Optional<LoginModel> byId = loginModelRepository.findById(loginModel.getEmail());

        // If user is present, return login successful
        if (byId.isPresent()) {
            if (byId.get().getPassword().equals(loginModel.getPassword()))
                return new ResponseModel(ResponseModel.SUCCESS, "Logged In Successfully");
            return new ResponseModel(ResponseModel.FAILURE, "Incorrect Password");
        }

        // Else return failure
        return new ResponseModel(ResponseModel.FAILURE, "Email not found");
    }
}
