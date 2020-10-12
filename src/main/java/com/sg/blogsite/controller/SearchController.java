/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.controller;

import com.sg.blogsite.dao.SearchTerm;
import com.sg.blogsite.model.Blog;
import com.sg.blogsite.model.User;
import com.sg.blogsite.service.BlogServiceLayer;
import com.sg.blogsite.service.UserServiceLayer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author mirandabeamer
 */
@Controller
public class SearchController {

    BlogServiceLayer service;
    UserServiceLayer userService;

    @Inject
    public SearchController(BlogServiceLayer service, UserServiceLayer userService) {
        this.service = service;
        this.userService = userService;
    }

    @RequestMapping(value = "search/blogs", method = RequestMethod.POST)
    @ResponseBody
    public List<Blog> searchBlogs(@RequestBody Map<String, String> searchMap) {
        Map<SearchTerm, String> criteria = new HashMap<>();
        String currentTerm = searchMap.get("hashtag");
        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteria.put(SearchTerm.HASHTAG, currentTerm);
        }
        String adminView = searchMap.get("isAdmin");
        boolean adminViewBoolean;
        if (adminView.equals("true")) {
            adminViewBoolean = true;
        } else {
            adminViewBoolean = false;
        }

        List<Blog> blogs = service.searchBlogs(criteria, adminViewBoolean);
        return blogs;
    }

    @RequestMapping(value = "search/recentBlogs", method = RequestMethod.POST)
    @ResponseBody
    public List<Blog> sortByRecent(@RequestBody Map<String, String> adminMap) {
        String adminView = adminMap.get("isAdmin");
        boolean adminViewBoolean = false;
        if (adminView.equals("true")) {
            adminViewBoolean = true;
        }
        return service.getNewestBlogs(adminViewBoolean);
    }

    @RequestMapping(value = "search/olderBlogs", method = RequestMethod.POST)
    @ResponseBody
    public List<Blog> sortByOldest(@RequestBody Map<String, String> adminMap) {
        String adminView = adminMap.get("isAdmin");
        boolean adminViewBoolean = false;
        if (adminView.equals("true")) {
            adminViewBoolean = true;
        }
        return service.getOldestBlogs(adminViewBoolean);
    }
}
