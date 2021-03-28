package com.example.services;

import com.example.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    /**
     * This is the service which handles all the requests of the admins
     */

    private final UserModelRepository userModelRepository;

    @Autowired
    public AdminService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }


}
