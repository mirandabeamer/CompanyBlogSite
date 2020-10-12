/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.dao;

import com.sg.blogsite.model.Blog;
import com.sg.blogsite.model.Page;
import com.sg.blogsite.model.Picture;
import com.sg.blogsite.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mirandabeamer
 */
public interface BlogSiteDao {
    
    public Blog addBlog(Blog blog, User user);
    
    public void deleteBlog(int blogId);
    
    public void editBlog(Blog blog);
    
    public Blog getBlog(int blogId);
    
    public List<Blog> getAllBlogs();
    
    public List<Blog> getAllApprovedBlogs(int limit);
    
    public List<Blog> getAllUnapprovedBlogs();
    
    public List<Blog> getNewestBlogs(boolean adminView);
    
    public List<Blog> getOldestBlogs(boolean adminView);
    
    public List<Blog> searchBlogs(Map<SearchTerm, String> criteria, boolean adminView);
    
    public Picture addPicture(Picture picture);

    public void deletePicture(int pictureId);

    public Picture getPictureById(int pictureId); 
    
    public Page addPage(Page page);
    
    public void deletePage(int pageId);
    
    public void editPage(Page page);
    
    public Page getPage(int pageId);
    
    public List<Page> getAllPages();

}
