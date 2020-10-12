/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.model;

import java.util.Objects;

/**
 *
 * @author mirandabeamer
 */
public class Picture {
    private int pictureId;
    private String filename;

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.pictureId;
        hash = 53 * hash + Objects.hashCode(this.filename);
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
        final Picture other = (Picture) obj;
        if (this.pictureId != other.pictureId) {
            return false;
        }
        if (!Objects.equals(this.filename, other.filename)) {
            return false;
        }
        return true;
    }
    
    
}
