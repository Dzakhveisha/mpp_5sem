package com.bsuir.spp.tasklist.service;

import com.bsuir.spp.tasklist.dao.jpa.UserRepository;
import com.bsuir.spp.tasklist.dao.model.User;
import com.bsuir.spp.tasklist.service.exception.ArgumentNotValidException;
import com.bsuir.spp.tasklist.service.exception.EntityAlreadyExistException;
import com.bsuir.spp.tasklist.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User", id);
        }
    }

    public User registrate(User user) {
        if (user.getUsername() == null || user.getPasswordHash() == null) {
            throw new ArgumentNotValidException("Not enough parameters!");
        }
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new EntityAlreadyExistException(user.getUsername(), "User");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return  user.get();
        } else {
            throw new EntityNotFoundException("User", id);
        }
    }

    public User getByUsername(String name) {
        Optional<User> userOptional = userRepository.findByUsername(name);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("User");
        }
    }
}
