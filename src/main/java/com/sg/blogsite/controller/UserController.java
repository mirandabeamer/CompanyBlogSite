/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.controller;

import com.sg.blogsite.model.User;
import com.sg.blogsite.service.UserServiceLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mirandabeamer
 */
@Controller
public class UserController {
   private UserServiceLayer service;
   private PasswordEncoder encoder;
   
   @Inject
   public UserController(UserServiceLayer service, PasswordEncoder encoder){
       this.service = service;
       this.encoder = encoder;
   }
   
   
   @RequestMapping(value="/displayUserList", method=RequestMethod.GET)
   public String displayUserList(Map<String, Object> model){
       List users = service.getAllUsers();
       model.put("users", users);
       return "displayUserList";
   }
   
   @RequestMapping(value="/displayUserForm", method=RequestMethod.GET)
   public String displayUserForm(Map<String, Object> model){
       return "addUser";
   }
   
   @RequestMapping(value="/addUser", method=RequestMethod.POST)
   public String addUser(HttpServletRequest req){
       User newUser = new User();
       newUser.setUsername(req.getParameter("username"));
       String clearPW = req.getParameter("password");
       String hashPw = encoder.encode(clearPW);
       newUser.setPassword(hashPw);
       List<String> authorityList = new ArrayList();
       authorityList.add("ROLE_USER");
       if(null !=req.getParameter("isAdmin")){
           authorityList.add("ROLE_ADMIN");
       }
       newUser.setAuthorities(authorityList);
       service.addUser(newUser);
       return "redirect:displayUserList";
   }
   
   @RequestMapping(value="/deleteUser", method=RequestMethod.GET)
   public String deleteUser(@RequestParam("username") String username, 
           Map<String, Object> model){
       service.deleteUser(username);
       return "redirect:displayUserList";
   }
}
