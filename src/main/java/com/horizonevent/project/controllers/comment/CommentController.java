package com.horizonevent.project.controllers.comment;

import com.horizonevent.project.models.Comment;
import com.horizonevent.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listComments(){
        List<Comment> comments = (List<Comment>) commentService.findAll();
        if (comments.isEmpty()){
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
}

  