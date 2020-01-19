package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Post;
import com.horizonevent.project.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    //Receive a single post
    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPost(@PathVariable("id") long id) {
        Post post = postService.findById(id);
        if (post == null) {
            System.out.println("Post with id " + id + " not found");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }


    //Create a new post
    @PostMapping("/posts")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createPost(@RequestBody Post post, UriComponentsBuilder ucBuilder) {
        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        Post post1 = new Post(post.getUser(), post.getTitle(), post.getContent(), post.getComments(), date, post.getShareStatus());
        postService.save(post1);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //Update Post
    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
        Post currentPost = postService.findById(id);

        if(currentPost == null) {
            System.out.println("Post with id " + id + " not fount");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }

        currentPost.setTitle(post.getTitle());
        currentPost.setContent(post.getContent());
        currentPost.setComments(post.getComments());
        currentPost.setDate(post.getDate());
        currentPost.setShareStatus(post.getShareStatus());

        postService.save(currentPost);
        return new ResponseEntity<Post>(currentPost, HttpStatus.OK);
    }

    //Delete a Post
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") long id) {
        Post post = postService.findById(id);
        if (post == null) {
            System.out.println("Unable to delete. Post id " + id + " not fount");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }

        postService.remove(id);
        return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
    }

}
