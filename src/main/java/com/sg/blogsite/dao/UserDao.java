/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.dao;

import com.sg.blogsite.model.Blog;
import com.sg.blogsite.model.User;
import java.util.List;

/**
 *
 * @author mirandabeamer
 */
public interface UserDao {
    public User addUser(User newUser);
    
    public User getUser(String username);
    
    public void deleteUser(String username);
    
    public List<User> getAllUsers();
    
    public User getUserForBlog(int blogId);
}
