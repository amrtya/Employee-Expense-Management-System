package com.example.controllers;

import com.example.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public void getAllUsers()
    {

    }

//    @PutMapping(path = "user/{id}")
//    public void updateUser(@PathVariable("id") String userId)
//    {
//
//    }

    @DeleteMapping(path = "user/{id}")
    public void deleteUser(@PathVariable("id") String userId)
    {

    }
}
