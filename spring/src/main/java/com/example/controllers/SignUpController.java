package com.example.controllers;

import com.example.models.ResponseModel;
import com.example.models.UserModel;
import com.example.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "signup")
public class SignUpController {

    private final SignupService signupService;

    @Autowired
    public SignUpController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseModel saveUser(@RequestBody UserModel userModel)
    {
        return signupService.saveUser(userModel);
    }
}
