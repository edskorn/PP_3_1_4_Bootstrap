package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import java.util.Locale;
import java.util.Optional;

@Service
public class RoleFormatter implements Formatter<Role> {
    @Autowired
    RoleDao roleDao;

    @Override
    public String print(Role object, Locale locale) {
        return object.getId().toString();
    }

    @Override
    public Role parse(String text, Locale locale) {
        long id = Long.parseLong(text);
        Optional<Role> role = roleDao.findById(id);
        return role.orElse(null);
    }
}