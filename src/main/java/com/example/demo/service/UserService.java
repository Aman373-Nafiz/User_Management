package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ParentModel createParent(ParentModel parent) {
        return userRepository.save(parent);
    }

    public ChildModel createChild(Long parentId, ChildModel child) {
        ParentModel parent = (ParentModel) userRepository.findById(parentId).orElseThrow();
        child.setParent(parent);
        return userRepository.save(child);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel updateUser(Long id, UserModel updatedUser) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));


        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());


        if (existingUser instanceof ParentModel && updatedUser instanceof ParentModel) {
            ParentModel existingParent = (ParentModel) existingUser;
            ParentModel updatedParent = (ParentModel) updatedUser;

            existingParent.setStreet(updatedParent.getStreet());
            existingParent.setCity(updatedParent.getCity());
            existingParent.setState(updatedParent.getState());
            existingParent.setZip(updatedParent.getZip());
        }

        // Save and return the updated user
        return userRepository.save(existingUser);
    }
}