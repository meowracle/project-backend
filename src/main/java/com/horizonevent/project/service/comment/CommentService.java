package com.horizonevent.project.service.comment;

import com.horizonevent.project.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Iterable<Comment> findAll();
    Optional<Comment> findById(Long id);
    void save(Comment comment);
    void remove (Long id);
    List<Comment> findAllByPostId(Long postId);
}
