package com.example.controllers;

import com.example.models.ResponseModel;
import com.example.models.ResponseModelSinglePayload;
import com.example.models.UserModel;
import com.example.models.UserReceiver;
import com.example.services.SignupService;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "signup")
public class SignUpController {

    /**
     * This Controller class provides the Sign up endpoint
     */

    private final SignupService signupService;

    @Autowired
    public SignUpController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseModelSinglePayload<UserModel> saveUser(@RequestBody UserReceiver userReceiver)
    {
        return signupService.saveUser(userReceiver);
    }
}
