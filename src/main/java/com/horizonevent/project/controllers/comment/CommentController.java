package com.horizonevent.project.controllers.comment;

import com.horizonevent.project.models.Comment;
import com.horizonevent.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/api/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listComments(){
        List<Comment> comments = (List<Comment>) commentService.findAll();
        if (comments.isEmpty()){
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
    @RequestMapping(value = "/api/comments", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createComment(@RequestBody Comment comment, UriComponentsBuilder uriComponentsBuilder){
        System.out.println("Creating Comments" + comment.getDescription());
        commentService.save(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/comments/{id}").buildAndExpand(comment.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/api/comments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Comment>> getComments(@PathVariable("id") long id){
        Optional<Comment> comment = commentService.findById(id);
        if(comment == null){
            System.out.printf("Comment with id" + id + "not found");
            return new ResponseEntity<Optional<Comment>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Comment>>(comment, HttpStatus.OK);
    }
}

  