package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Picture;
import com.horizonevent.project.models.Post;
import com.horizonevent.project.repository.post.PostRepository;
import com.horizonevent.project.service.picture.PictureService;
import com.horizonevent.project.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    Environment environment;

    @Autowired
    PictureService pictureService;

    @Autowired
    PostRepository postRepository;

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
    public ResponseEntity<Post> createPost(@RequestBody Post post, UriComponentsBuilder ucBuilder) {
        List<Picture> newPostPictures = new ArrayList<>();
        if (post.getPictures() != null) {
            for (Picture picture : post.getPictures()) {
                pictureService.save(picture);
                newPostPictures.add(picture);
            }
        }
        post.setPictures(newPostPictures);
        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        Post currentPost = new Post(post.getUser(), post.getTitle(), post.getContent(), post.getComments(), date, post.getShareStatus(), post.getPictures());
        postService.save(currentPost);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri());
        return new ResponseEntity<Post>(headers, HttpStatus.CREATED);
    }

    //Update Post
    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
        Post currentPost = postService.findById(id);
        List<Picture> newPostPictures = new ArrayList<>();
        if (post.getPictures() != null) {
            for (Picture picture : post.getPictures()) {
                pictureService.save(picture);
                newPostPictures.add(picture);
            }
        }
        post.setPictures(newPostPictures);

        if (currentPost == null) {
            System.out.println("Post with id " + id + " not fount");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }

        currentPost.setTitle(post.getTitle());
        currentPost.setContent(post.getContent());
        currentPost.setComments(post.getComments());
        currentPost.setDate(post.getDate());
        currentPost.setShareStatus(post.getShareStatus());
        currentPost.setPictures(post.getPictures());

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
