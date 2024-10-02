//package com.jsbyrd02.springboot.chess_notation_trainer.User;
//
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    private final UserDAO userRepository;
//
//    @Autowired
//    public UserService(UserDAO userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//
//    public User findById(int userId) {
//        User user = userRepository.findById(userId);
//
//        if (user == null) {
//            throw new RuntimeException("Did not find user_id - " + userId);
//        }
//
//        return user;
//    }
//
//    @Transactional
//    public User save(User user) {
//        return userRepository.save(user);
//    }
//
//    @Transactional
//    public void deleteById(int userId) {
//        userRepository.deleteById(userId);
//    }
//}
