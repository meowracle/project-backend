package com.horizonevent.project.models;

import com.horizonevent.project.models.user.User;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToOne(targetEntity = Post.class)
    private Post post;

    public Comment() {
    }

    public Comment(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Comment(Long id, String description, User user, Post post) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

  