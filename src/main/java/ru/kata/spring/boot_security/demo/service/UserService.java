package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.PersonDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {
    void saveUser(User user);
    void removeUserById(long id);
    List<User> getAllUsers();
    List<Role> getAllRoles();
    User getUserById(long id);
    Optional<User> getUserByUsername(String username);
}
