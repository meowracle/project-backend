package com.horizonevent.project.service.user;

import com.horizonevent.project.models.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    void save(User user);
}
