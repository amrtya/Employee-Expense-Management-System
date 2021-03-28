package com.example.controllers;

import com.example.models.LoginModel;
import com.example.models.ResponseModel;
import com.example.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
public class LoginController {

    /**
     * This Controller class provides the endpoint for Login
     */

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseModel checkUser(@RequestBody LoginModel loginModel)
    {
        return loginService.checkUser(loginModel);
    }

}
