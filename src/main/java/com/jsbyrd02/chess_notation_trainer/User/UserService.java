package com.jsbyrd02.chess_notation_trainer.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        Optional<User> userOpt = userRepository.findById(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User with username: " + username + " not found");
        }

        User user = userOpt.get();

        // Create and return the Spring Security UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public User createUser(User user) {
        // Check to see if user already exists
        Optional<User> userOpt = this.getUser(user.getUsername());

        if (userOpt.isPresent()) {
            throw new RuntimeException("User with username '" + user.getUsername() + "' already exists. Please try another username.");
        }

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Set default values
        user.setEnabled(true);
        user.setDateCreated(Date.valueOf(LocalDate.now()));
        user.setLastLoginDate(Date.valueOf(LocalDate.now()));

        // Save the user
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findById(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Check if the provided password matches the stored encrypted password
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Update the last login date
                user.setLastLoginDate(Date.valueOf(LocalDate.now()));
                userRepository.save(user); // Save the updated user

                // Return the un-hashed password for authentication simplicity
                user.setPassword(password);

                return Optional.of(user);
            }
        }
        return Optional.empty(); // Return empty if the credentials don't match
    }

    public User updateUser(String username, User updatedUser) {
        return userRepository.findById(username).map(existingUser -> {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            existingUser.setLastLoginDate(Date.valueOf(LocalDate.now()));
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
}
