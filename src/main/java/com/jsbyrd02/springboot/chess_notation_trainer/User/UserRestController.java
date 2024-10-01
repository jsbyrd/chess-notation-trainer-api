package com.jsbyrd02.springboot.chess_notation_trainer.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        User employee = userService.findById(userId);
        if (employee == null) {
            throw new RuntimeException("user_id not found - " + userId);
        }
        return employee;
    }

    @PostMapping()
    public User addUser(@RequestBody User employee) {
        return userService.save(employee);
    }

    @PutMapping()
    public User updateUser(@RequestBody User employee) {
        return userService.save(employee);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        User employee = userService.findById(userId);

        if (employee == null) {
            throw new RuntimeException("user_id not found - " + userId);
        }

        userService.deleteById(userId);
    }
}
