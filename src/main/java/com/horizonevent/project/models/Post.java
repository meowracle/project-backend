package com.horizonevent.project.models;

import com.horizonevent.project.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 120, min = 6)
    private String title;

    @Size(min = 6)
    private String content;

    @OneToMany
    private List<Comment> comments;

    @NotNull
    private Date date;

    @NotNull
    private Boolean shareStatus;

    @OneToMany
    private List<Picture> pictures;

    public Post() {
    }

    public Post(User user, String title, String content, List<Comment> comments, Date date, Boolean shareStatus, List<Picture> pictures) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.date = date;
        this.shareStatus = shareStatus;
        this.pictures = pictures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Boolean shareStatus) {
        this.shareStatus = shareStatus;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
