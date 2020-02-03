package com.horizonevent.project.service.post;

import com.horizonevent.project.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Iterable<Post> findAll();
    Optional<Post> findById(Long id);
    void save(Post post);
    void remove(Long id);
}
