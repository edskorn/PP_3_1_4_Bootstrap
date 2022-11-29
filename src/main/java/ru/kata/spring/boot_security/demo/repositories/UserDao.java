package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    void deleteById(long id);
    List<User> getAllUsers();
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
}
