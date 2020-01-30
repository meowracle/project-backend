package com.horizonevent.project.service.user.impl;

import com.horizonevent.project.models.user.User;
import com.horizonevent.project.repository.UserRepository;
import com.horizonevent.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);

    }
}

  