package com.suraj.todo.todowebapp.service;

import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.UserDetailsImpl;
import com.suraj.todo.todowebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }

        UserDetails userDetails = new UserDetailsImpl(user);

        return userDetails;
    }
}
