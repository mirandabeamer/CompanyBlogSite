/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mirandabeamer
 */
public class Blog {
    private int blogId;
    @NotEmpty(message="Blog title required")
    @Length(max=50, message="Blog title may not exceed 50 characters")
    private String title;
    @NotEmpty(message="Blog content required")
    @Length(max=1000, message="blog content may not exceed 1000 characters")
    private String content;
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate expirationDate;
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate displayDate;
    private List<String> hashtags;
    private List<Picture> pictures;
    private User author;
    private LocalDate approvedOn;

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
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

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(LocalDate displayDate) {
        this.displayDate = displayDate;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDate getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.blogId;
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + Objects.hashCode(this.content);
        hash = 17 * hash + Objects.hashCode(this.expirationDate);
        hash = 17 * hash + Objects.hashCode(this.displayDate);
        hash = 17 * hash + Objects.hashCode(this.hashtags);
        hash = 17 * hash + Objects.hashCode(this.pictures);
        hash = 17 * hash + Objects.hashCode(this.author);
        hash = 17 * hash + Objects.hashCode(this.approvedOn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Blog other = (Blog) obj;
        if (this.blogId != other.blogId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.expirationDate, other.expirationDate)) {
            return false;
        }
        if (!Objects.equals(this.displayDate, other.displayDate)) {
            return false;
        }
        if (!Objects.equals(this.hashtags, other.hashtags)) {
            return false;
        }
        if (!Objects.equals(this.pictures, other.pictures)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.approvedOn, other.approvedOn)) {
            return false;
        }
        return true;
    }

    
    
    
}
