package com.horizonevent.project.service.post;

import com.horizonevent.project.models.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post findById(Long id);

    void save (Post post);

    void remove(Long id);
}
