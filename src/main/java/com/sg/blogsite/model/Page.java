/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.model;

import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author mirandabeamer
 */
public class Page {
    private int pageId;
    @NotEmpty(message="Heading required.")
    @Length(max=50, message="Heading must not be more than 50 characters in length")
    private String heading;
    private Picture picture;
    @NotEmpty(message="Content Required.")
    private String paragraph;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    
    
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.pageId;
        hash = 59 * hash + Objects.hashCode(this.heading);
        hash = 59 * hash + Objects.hashCode(this.picture);
        hash = 59 * hash + Objects.hashCode(this.paragraph);
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
        final Page other = (Page) obj;
        if (this.pageId != other.pageId) {
            return false;
        }
        if (!Objects.equals(this.heading, other.heading)) {
            return false;
        }
        if (!Objects.equals(this.paragraph, other.paragraph)) {
            return false;
        }
        if (!Objects.equals(this.picture, other.picture)) {
            return false;
        }
        return true;
    }

    
}
