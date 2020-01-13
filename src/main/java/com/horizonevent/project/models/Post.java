package com.horizonevent.project.models;

import com.horizonevent.project.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private Date date;

    @NotBlank
    private Boolean shareStatus;

    public Post() {
    }

    public Post(Long id, User user, String title, String content, Date date, Boolean shareStatus) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.date = date;
        this.shareStatus = shareStatus;
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
}
