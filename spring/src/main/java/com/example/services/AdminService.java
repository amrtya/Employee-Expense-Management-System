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
    
    public ResponseModelListPayload<UserModel> getAll() {
        return new ResponseModelListPayload<UserModel>(ResponseModel.SUCCESS, userModelRepository.getAll());
    }
    
    public Optional<UserModel> getUserByMail(String email) {
        return UserModelRepository.findUserByEmail(email);
    }
    
    public ResponseModel<UserModel> delUserByMail(String email)
    {
    	UserModelRepository.delete(email);
        return new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
    }
    
    @Transactional
    public ResponseModelSinglePayload<UserModel> updateUser(
            UserModel userModelCurrent,
            UserModel userModelToUpdate
    ) {

        UserModel userModel = userModelCurrent;

        if (!Objects.equals(userModel.getEmail(), userModelToUpdate.getEmail())) {
            userModel.setEmail(userModelToUpdate.getEmail());
        }
        if (!Objects.equals(userModel.getUsername(), userModelToUpdate.getUsername())) {
            userModel.setUsername(userModelToUpdate.getUsername());
        }
        if (!Objects.equals(userModel.getPassword(), userModelToUpdate.getPassword())) {
            userModel.setPassword(userModelToUpdate.getPassword());
        }
        if (!Objects.equals(userModel.getMobileNumber(), userModelToUpdate.getMobileNumber())) {
            userModel.setMobileNumber(userModelToUpdate.getMobileNumber());
        }
        if (!Objects.equals(userModel.getRole(), userModelToUpdate.getRole())) {
            userModel.setRole(userModelToUpdate.getRole());
        }

        // Return Successful with the updated user
        return new ResponseModelSinglePayload<UserModel>(
                ResponseModel.SUCCESS, "User Updated Successfully",
                userModel
        );

    }

}
