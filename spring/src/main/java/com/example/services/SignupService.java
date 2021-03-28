package com.example.services;

import com.example.models.LoginModel;
import com.example.models.ResponseModel;
import com.example.models.UserModel;
import com.example.repositories.LoginModelRepository;
import com.example.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    private final UserModelRepository userModelRepository;
    private final LoginModelRepository loginModelRepository;

    @Autowired
    public SignupService(UserModelRepository userModelRepository, LoginModelRepository loginModelRepository) {
        this.userModelRepository = userModelRepository;
        this.loginModelRepository = loginModelRepository;
    }

    public ResponseModel saveUser(UserModel userModel) {
        Optional<UserModel> userByEmail = userModelRepository.findUserByEmail(userModel.getEmail());

        if(userByEmail.isPresent())
            return new ResponseModel(ResponseModel.EMAIL_TAKEN, "This Email is already taken. Try another one.");


        userModelRepository.save(userModel);
        loginModelRepository.save(new LoginModel(userModel.getEmail(), userModel.getPassword()));

        return new ResponseModel(ResponseModel.SUCCESS, "User Added Successfully");
    }
}
