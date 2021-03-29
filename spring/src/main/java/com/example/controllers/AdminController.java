package com.example.controllers;

import com.example.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.models.*;
import com.example.repositories.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    /**
     * This Controller class gives the endpoints for the admins
     */

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseModelListPayload<UserModel> getAllUsers()
    {
    	ResponseModelListPayload<UserModel> allUser = adminService.getAll();
    	return allUser;
    }

    @PutMapping(path = "user/{email}")
    public void updateUser(@PathVariable("email") String email)
    {
    	Optional<UserModel> userByMail = adminService.getUserByMail(email);
    	if(userByMail.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "User not found");
    	
    	return adminService.updateUser(userByMail.get(), UserModelToUpdate);
    }

    @DeleteMapping(path = "user/{email}")
    public void deleteUser(@PathVariable("email") String email)
    {
    	Optional<UserModel> userByMail = adminService.getUserByMail(email);
    	
    	if(userByMail.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "User not found");
    	
    	return adminService.delUserByMail(email);
    }
}
