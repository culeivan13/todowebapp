package com.suraj.todo.todowebapp.service;

import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.SignUpDTO;
import com.suraj.todo.todowebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User saveUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signUpDTO.getPassword()));
        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }
}
