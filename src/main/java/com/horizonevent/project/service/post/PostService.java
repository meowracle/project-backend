package com.horizonevent.project.service.post;

import com.horizonevent.project.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Iterable<Post> findAll();
    Optional<Post> findById(Long id);
    void save(Post post);
    void remove(Long id);
    Page<Post> findAllByTitleContaining(String tittle, Pageable pageable);
}
