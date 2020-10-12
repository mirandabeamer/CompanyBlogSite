/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.service;

import com.sg.blogsite.dao.UserDao;
import com.sg.blogsite.model.User;
import java.util.List;

/**
 *
 * @author mirandabeamer
 */
public class UserServiceLayerImpl implements UserServiceLayer {

    private UserDao dao;
    
    public UserServiceLayerImpl(UserDao dao){
        this.dao = dao;
    }
    @Override
    public User addUser(User newUser) {
        return dao.addUser(newUser);
    }

    @Override
    public void deleteUser(String username) {
        dao.deleteUser(username);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public User getUser(String username) {
        return dao.getUser(username);
    }

    @Override
    public User getUserForBlog(int blogId) {
        return dao.getUserForBlog(blogId);
    }
    
}
