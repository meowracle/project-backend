package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Post;
import com.horizonevent.project.security.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    //Receive all posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> listAllPosts() {
        List<Post> posts =
    }
}
