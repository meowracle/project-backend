package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Post;
import com.horizonevent.project.security.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    //Receive all posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> listAllPosts() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    //Create a new post
    @PostMapping("/create-post")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createPost (@RequestBody Post post, UriComponentsBuilder ucBuilder) {
        postService.save(post);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
