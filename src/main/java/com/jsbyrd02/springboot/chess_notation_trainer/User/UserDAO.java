package com.jsbyrd02.springboot.chess_notation_trainer.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    public User findById(int userId) {
        return entityManager.find(User.class, userId);
    }

    public User save(User user) {
        return entityManager.merge(user);
    }

    public void deleteById(int userId) {
        User user = entityManager.find(User.class, userId);
        entityManager.remove(user);
    }
}
