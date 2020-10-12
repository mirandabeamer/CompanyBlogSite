/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.controller;

import com.sg.blogsite.model.Blog;
import com.sg.blogsite.model.Page;
import com.sg.blogsite.model.User;
import com.sg.blogsite.service.BlogServiceLayer;
import com.sg.blogsite.service.UserServiceLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author mirandabeamer
 */
@Controller
public class BlogController {

    BlogServiceLayer service;
    UserServiceLayer userService;
    String errorMessage = "";

    @Inject
    public BlogController(BlogServiceLayer service, UserServiceLayer userService) {
        this.userService = userService;
        this.service = service;
    }

    @RequestMapping(value = {"/", "/displayHomePage"}, method = RequestMethod.GET)
    public String displayHomePage(Model model) {
        List<Blog> blogs = service.getAllApprovedBlogs(3);
        for (Blog blog : blogs) {
            User user = userService.getUserForBlog(blog.getBlogId());
            blog.setAuthor(user);
        }
        model.addAttribute("blogs", blogs);
        List<Blog> unapprovedBlogs = service.getAllUnapprovedBlogs();
        int numPendingApprovals = unapprovedBlogs.size();
        model.addAttribute("numPendingApprovals", numPendingApprovals);
        //get any pages that have been added...
        List<Page> pages = service.getAllPages();
        model.addAttribute("pages", pages);
        return "home";
    }

    @RequestMapping(value = "/displayNewBlogForm", method = RequestMethod.GET)
    public String displayNewBlogForm(Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "AddBlog";
    }

    @RequestMapping(value = "/addBlog", method = RequestMethod.POST)
    public String addBlog(HttpServletRequest request) {
        Blog blog = new Blog();
        //get username and associate with blog 
        String username = request.getParameter("username");
        User user = userService.getUser(username);
        blog.setAuthor(user);
        //if user has admin role - automatically approve blog
        if (user.getAuthorities().contains("ROLE_ADMIN")) {
            blog.setApprovedOn(LocalDate.now());
        }
        //get title and content - ensure content is not left empty, if so display error message
        blog.setTitle(request.getParameter("title"));
        String content = request.getParameter("textarea");
        if (content.isEmpty()) {
            errorMessage = "Content is required.";
            return "redirect:displayNewBlogForm";
        }
        blog.setContent(content);
        errorMessage = "";

        //set dates if not null. 
        String date = request.getParameter("displayDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime;
        if (!date.isEmpty()) {
            dateTime = LocalDate.parse(date, formatter);
            blog.setDisplayDate(dateTime);
        }

        date = request.getParameter("expirationDate");
        if (!date.isEmpty()) {
            dateTime = LocalDate.parse(date, formatter);
            blog.setExpirationDate(dateTime);
        }

        String hashtagsParam = request.getParameter("hashtags");
        hashtagsParam = hashtagsParam.replaceAll("#", "");
        String str[] = hashtagsParam.split(", ");
        List<String> hashtags = new ArrayList<String>();
        hashtags = Arrays.asList(str);
        blog.setHashtags(hashtags);

        service.addBlog(blog, user);
        return "redirect:displayHomePage";
    }

    @RequestMapping(value = "/viewAllBlogs", method = RequestMethod.GET)
    public String viewAllBlogs(Model model) {
        List<Blog> blogs = service.getAllApprovedBlogs(0);
        model.addAttribute("blogs", blogs);
        model.addAttribute("errorMessage", errorMessage);
        //get any pages that have been added...
        List<Page> pages = service.getAllPages();
        model.addAttribute("pages", pages);
        //ensure that searches only reveal user approved blogs
        Boolean adminView = false;
        model.addAttribute("adminView", adminView);
        return "ViewAllBlogs";
    }

    @RequestMapping(value = "/viewBlog", method = RequestMethod.GET)
    public String viewBlog(HttpServletRequest request, Model model) {
        String blogIdParameter = request.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdParameter);
        Blog blog = service.getBlog(blogId);

        //for admin - show whether blog has been approved or not
        String approvalStatus;
        LocalDate approvedDate = blog.getApprovedOn();
        if (approvedDate == null) {
            approvalStatus = "Pending Approval";
            model.addAttribute("approvalStatus", approvalStatus);
        } else {
            model.addAttribute("approvedDate", approvedDate);
        }

        User user = userService.getUserForBlog(blogId);
        blog.setAuthor(user);
        model.addAttribute("blog", blog);
        return "ViewBlog";
    }

    @RequestMapping(value = "/deleteBlog", method = RequestMethod.GET)
    public String deleteBlog(HttpServletRequest request) {
        String blogIdParameter = request.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdParameter);
        service.deleteBlog(blogId);
        return "redirect:viewAllBlogsAdmin";
    }

    @RequestMapping(value = "/DisplayEditBlog", method = RequestMethod.GET)
    public String DisplayEditBlog(HttpServletRequest request, Model model) {
        //get blog to be edited and add to model
        String blogIdParameter = request.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdParameter);
        Blog blog = service.getBlog(blogId);
        model.addAttribute("blog", blog);
        //get dates as strings so they can be auto filled into form
        String displayDateString = blog.getDisplayDate().toString();
        String expirationDateString = blog.getExpirationDate().toString();
        model.addAttribute("displayDateString", displayDateString);
        model.addAttribute("expirationDateString", expirationDateString);
        model.addAttribute(errorMessage, "errorMessage");

        //get hashtags as one string so they can be auto filled into form
        //but only if there are hashtags associated with the post.
        List<String> hashtags = blog.getHashtags();
        if (hashtags != null) {
            String hashtagString = "";
            for (String hashtag : hashtags) {
                hashtagString = hashtagString + "#" + hashtag + ", ";
            }
            model.addAttribute("hashtagString", hashtagString);
        }

        return "EditBlog";
    }

    @RequestMapping(value = "editBlog", method = RequestMethod.POST)
    public String editBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            return "EditBlog";
        }
        //get hashtag string and parse into individual hashtags
        String hashtagsParam = request.getParameter("hashtags");
        hashtagsParam = hashtagsParam.replaceAll("#", "");
        String str[] = hashtagsParam.split(", ");
        List<String> hashtags = new ArrayList<>();
        hashtags = Arrays.asList(str);
        blog.setHashtags(hashtags);

        //both dates are returned as strings - parse dates and set on blog
        String displayDateStr = request.getParameter("dispDate");
        String expirationDateStr = request.getParameter("expDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //parse dates 
        LocalDate displayDate = LocalDate.parse(displayDateStr, formatter);
        LocalDate expirationDate = LocalDate.parse(expirationDateStr, formatter);
        blog.setDisplayDate(displayDate);
        blog.setExpirationDate(expirationDate);

        service.editBlog(blog);
        errorMessage = "";

        return "redirect:viewAllBlogs";
    }

    @RequestMapping(value = "/approveBlogs", method = RequestMethod.GET)
    public String approveBlogs(HttpServletRequest request, Model model) {
        List<Blog> unapprovedBlogs = service.getAllUnapprovedBlogs();
        model.addAttribute("blogs", unapprovedBlogs);
        return "pendingBlogs";
    }

    @RequestMapping(value = "/reviewBlog", method = RequestMethod.GET)
    public String reviewBlog(HttpServletRequest request, Model model) {
        String blogIdParameter = request.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdParameter);
        Blog blog = service.getBlog(blogId);
        model.addAttribute("blog", blog);

        String approvalStatus;
        if (blog.getApprovedOn() == null) {
            approvalStatus = "Pending Approval";
        } else {
            approvalStatus = "Approved " + blog.getApprovedOn();
        }
        model.addAttribute("approvalStatus", approvalStatus);
        return "ViewBlog";
    }

    @RequestMapping(value = "/approveBlog", method = {RequestMethod.GET, RequestMethod.POST})
    public String approveBlog(HttpServletRequest request) {
        String blogIdParameter = request.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdParameter);
        Blog blog = service.getBlog(blogId);

        blog.setApprovedOn(LocalDate.now());
        service.editBlog(blog);
        return "redirect:approveBlogs";
    }

    @RequestMapping(value = "/viewAllBlogsAdmin", method = RequestMethod.GET)
    public String viewAllBlogsAdmin(Model model) {
        List<Blog> blogs = service.getAllBlogs();
        model.addAttribute("blogs", blogs);
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        for (Blog blog : blogs) {
            String approvalStatus;
            if (blog.getApprovedOn() == null) {
                approvalStatus = "Pending Approval";
            } else {
                approvalStatus = "Approved " + blog.getApprovedOn();
            }
            model.addAttribute("approvalStatus", approvalStatus);
        }
        //letting search methods know admin vs user view. 
        Boolean adminView = true;
        model.addAttribute("adminView", adminView);
        return "ViewAllBlogs";
    }

    @RequestMapping(value = "/displayPageManagement", method = RequestMethod.GET)
    public String displayPageManagement(Model model) {
        List<Page> pages = service.getAllPages();
        model.addAttribute("pages", pages);
        return "pageManagement";
    }

    @RequestMapping(value = "/displayAddNewPage", method = RequestMethod.GET)
    public String displayAddNewPage(Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "addNewPage";
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.POST)
    public String addPage(HttpServletRequest request) {
        Page page = new Page();
        page.setHeading(request.getParameter("heading"));
        String content = request.getParameter("textarea");

        if (content.isEmpty()) {
            errorMessage = "Content is required.";
            return "redirect:displayAddNewPage";
        }
        page.setParagraph(content);
        service.addPage(page);
        return "redirect:displayHomePage";
    }

    @RequestMapping(value = "/displayStaticPage", method = RequestMethod.GET)
    public String displayStaticPage(Model model, HttpServletRequest request) {
        String pageIdParameter = request.getParameter("pageId");
        int pageId = Integer.parseInt(pageIdParameter);
        Page page = service.getPage(pageId);
        model.addAttribute("page", page);
        return "staticPage";
    }

    @RequestMapping(value = "/deletePage", method = RequestMethod.GET)
    public String deletePage(HttpServletRequest request) {
        String pageIdParameter = request.getParameter("pageId");
        int pageId = Integer.parseInt(pageIdParameter);
        service.deletePage(pageId);
        return "redirect:displayPageManagement";
    }

    @RequestMapping(value = "/displayEditStaticPage", method = RequestMethod.GET)
    public String displayEditStaticPage(Model model, HttpServletRequest request) {
        String pageIdParameter = request.getParameter("pageId");
        int pageId = Integer.parseInt(pageIdParameter);
        Page page = service.getPage(pageId);
        model.addAttribute("page", page);
        return "editStaticPage";
    }

    @RequestMapping(value = "editPage", method = RequestMethod.POST)
    public String editpage(@Valid
            @ModelAttribute("page") Page page, BindingResult result) {
        if (result.hasErrors()) {
            errorMessage = "Both heading and content required";
            return "editStaticPage";
        }
        errorMessage = "";
        service.editPage(page);
        return "redirect:displayPageManagement";
    }

}
