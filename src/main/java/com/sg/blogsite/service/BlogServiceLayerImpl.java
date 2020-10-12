/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.service;

import com.sg.blogsite.dao.BlogSiteDao;
import com.sg.blogsite.dao.SearchTerm;
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
public class BlogServiceLayerImpl implements BlogServiceLayer {

    BlogSiteDao dao;

    public BlogServiceLayerImpl(BlogSiteDao dao) {
        this.dao = dao;
    }

    @Override
    public Blog addBlog(Blog blog, User user) {
        return dao.addBlog(blog, user);
    }

    @Override
    public void deleteBlog(int blogId) {
        dao.deleteBlog(blogId);
    }

    @Override
    public void editBlog(Blog blog) {
        dao.editBlog(blog);
    }

    @Override
    public Blog getBlog(int blogId) {
        return dao.getBlog(blogId);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return dao.getAllBlogs();
    }
    
    @Override
    public List<Blog> getAllApprovedBlogs(int limit){
        return dao.getAllApprovedBlogs(limit);
    }

    public List<Blog> getAllUnapprovedBlogs(){
        return dao.getAllUnapprovedBlogs();
    }

    @Override
    public List<Blog> searchBlogs(Map<SearchTerm, String> criteria, boolean adminView) {
        return dao.searchBlogs(criteria, adminView);
    }

    @Override
    public Picture addPicture(Picture picture) {
        return dao.addPicture(picture);
    }

    @Override
    public void deletePicture(int pictureId) {
        dao.deletePicture(pictureId);
    }

    @Override
    public Picture getPictureById(int pictureId) {
        return dao.getPictureById(pictureId);
    }

    @Override
    public List<Blog> getNewestBlogs(boolean adminView) {
        return dao.getNewestBlogs(adminView);
    }

    @Override
    public List<Blog> getOldestBlogs(boolean adminView) {
        return dao.getOldestBlogs(adminView);
    }

    @Override
    public Page addPage(Page page) {
        return dao.addPage(page);
    }

    @Override
    public void deletePage(int pageId) {
        dao.deletePage(pageId);
    }

    @Override
    public void editPage(Page page) {
        dao.editPage(page);
    }

    @Override
    public Page getPage(int pageId) {
        return dao.getPage(pageId);
    }

    @Override
    public List<Page> getAllPages() {
        return dao.getAllPages();
    }

}
