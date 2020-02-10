package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Comment;
import com.horizonevent.project.models.user.User;
import com.horizonevent.project.service.comment.CommentService;
import com.horizonevent.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    //Receive list comment
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listComments() {
        List<Comment> comments = (List<Comment>) commentService.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/posts/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listCommentsByPostId(@PathVariable long id) {
        List<Comment> comments = (List<Comment>) commentService.findAllByPostId(id);
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }


    //create a new comment
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createComment(@RequestBody Comment comment, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Creating Comments" + comment.getDescription());
        commentService.save(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/comments/{id}").buildAndExpand(comment.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //get a single comment
    /*    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")*/
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Comment>> getComments(@PathVariable("id") long id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment == null) {
            System.out.printf("Comment with id" + id + "not found");
            return new ResponseEntity<Optional<Comment>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Comment>>(comment, HttpStatus.OK);
    }

    //delete a comment
    /*    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")*/
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
        System.out.println("Fetching & delete Comments with id" + id);
        Optional<Comment> comment = commentService.findById(id);
        if (comment == null) {
            System.out.println("Unable to delete Comment with id " + id + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
        commentService.remove(id);
        return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/comments/posts/{id}")
    public ResponseEntity<List<Comment>> deleteAllCommentByPostId (@PathVariable("id") long id) {
        List<Comment> comments = (List<Comment>) commentService.findAllByPostId(id);
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(HttpStatus.NOT_FOUND);
        }
        commentService.removeAllCommentByPostID(id);
        return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
    }

    // update comment
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Optional<Comment>> updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
        System.out.println("updating Comment " + id);
        Optional<Comment> currentComment = commentService.findById(id);
        if (currentComment == null) {
            System.out.println("Comment with id" + id + "not found");
            return new ResponseEntity<Optional<Comment>>(HttpStatus.NOT_FOUND);
        }
        currentComment.get().setDescription(comment.getDescription());
        currentComment.get().setUser(comment.getUser());
        currentComment.get().setPost(comment.getPost());
        commentService.save(currentComment.get());
        return new ResponseEntity<Optional<Comment>>(currentComment, HttpStatus.OK);
    }
}

  