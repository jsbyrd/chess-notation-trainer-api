package com.jsbyrd02.chess_notation_trainer.User;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        Optional<User> user = userService.getUser(username);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Example Request: POST /api/users/login?username=johndoe&password=secretpassword
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = userService.loginUser(username, password);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build()); // Return 401 Unauthorized if login fails
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
        User updatedUser = userService.updateUser(username, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
