package com.example.demo.controller;
import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/parent")
    public ParentModel createParent(@RequestBody ParentModel parent) {
        return userService.createParent(parent);
    }

    @PostMapping("/child/{parentId}")
    public ChildModel createChild(@PathVariable Long parentId, @RequestBody ChildModel child) {
        return userService.createChild(parentId, child);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/")
    public List<?> getAllUsers() {
        return userService.getAllUsers();
    }
    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        return userService.updateUser(id, user);
    }
}