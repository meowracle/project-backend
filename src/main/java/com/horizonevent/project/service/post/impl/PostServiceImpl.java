package com.horizonevent.project.service.post.impl;

import com.horizonevent.project.models.Post;
import com.horizonevent.project.repository.post.PostRepository;
import com.horizonevent.project.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;


    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAllByShareStatusIsTrue();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);

    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);

    }

    @Override
    public List<Post> findAllByTitleContaining(String tittle) {
        return postRepository.findAllByTitleContaining(tittle);
    }

    @Override
    public Iterable<Post> findAllByUser_Id(Long id) {
        return postRepository.findAllByUser_Id(id);
    }

}
