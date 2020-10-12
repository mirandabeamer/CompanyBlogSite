/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.dao;

import com.sg.blogsite.model.Blog;
import com.sg.blogsite.model.Page;
import com.sg.blogsite.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mirandabeamer
 */
public class BlogSiteDaoTest {

    private BlogSiteDao dao;
    private UserDao userDao;

    public BlogSiteDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("blogsiteDao", BlogSiteDao.class);
        userDao = ctx.getBean("userDao", UserDao.class);

        List<Blog> blogs = dao.getAllBlogs();
        for (Blog blog : blogs) {
            dao.deleteBlog(blog.getBlogId());
        }

        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getUsername());
        }

        List<Page> pages = dao.getAllPages();
        for (Page page : pages) {
            dao.deletePage(page.getPageId());
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addBlog method, of class BlogSiteDao.
     */
    @Test
    public void testAddGetBlog() {
        Blog blog = new Blog();
        blog.setTitle("First Blog Post");
        blog.setContent("This is <b>A LOT</b> of important content");
        String hashtag1 = "#firstPost";
        String hashtag2 = "#Blog";

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        String authority = ("ADMIN");
        List<String> authorities = new ArrayList();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userDao.addUser(user);

        List<String> hashtags = new ArrayList();
        hashtags.add(hashtag2);
        hashtags.add(hashtag1);
        blog.setHashtags(hashtags);
        blog = dao.addBlog(blog, user);
        Blog fromDao = dao.getBlog(blog.getBlogId());
        assertEquals(blog, fromDao);
    }

    /**
     * Test of deleteBlog method, of class BlogSiteDao.
     */
    @Test
    public void testDeleteBlog() {
        Blog blog = new Blog();
        blog.setTitle("First Blog Post");
        blog.setContent("This is <b>A LOT</b> of important content");
        String hashtag1 = "#firstPost";
        String hashtag2 = "#Blog";

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        String authority = ("ADMIN");
        List<String> authorities = new ArrayList();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userDao.addUser(user);

        List<String> hashtags = new ArrayList();
        hashtags.add(hashtag2);
        hashtags.add(hashtag1);
        blog.setHashtags(hashtags);
        blog = dao.addBlog(blog, user);
        Blog fromDao = dao.getBlog(blog.getBlogId());
        assertEquals(blog, fromDao);

        dao.deleteBlog(blog.getBlogId());
        assertNull(dao.getBlog(blog.getBlogId()));
    }

    /**
     * Test of editBlog method, of class BlogSiteDao.
     */
    @Test
    public void testEditBlog() {
        Blog blog = new Blog();
        blog.setTitle("First Blog Post");
        blog.setContent("This is <b>A LOT</b> of important content");
        String hashtag1 = "#firstPost";
        String hashtag2 = "#Blog";

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        String authority = ("ADMIN");
        List<String> authorities = new ArrayList();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userDao.addUser(user);

        List<String> hashtags = new ArrayList();
        hashtags.add(hashtag2);
        hashtags.add(hashtag1);
        blog.setHashtags(hashtags);
        blog = dao.addBlog(blog, user);
        Blog fromDao = dao.getBlog(blog.getBlogId());
        assertEquals(blog, fromDao);

        blog.setTitle("The Blog Post");
        dao.editBlog(blog);
        fromDao = dao.getBlog(blog.getBlogId());
        assertEquals(blog, fromDao);

        blog.setApprovedOn(LocalDate.now());
        dao.editBlog(blog);
        fromDao = dao.getBlog(blog.getBlogId());
        assertEquals(blog, fromDao);
    }

    /**
     * Test of getAllBlogs method, of class BlogSiteDao.
     */
    @Test
    public void testGetAllBlogs() {
        Blog blog = new Blog();
        blog.setTitle("First Blog Post");
        blog.setContent("This is <b>A LOT</b> of important content");
        String hashtag1 = "#firstPost";
        String hashtag2 = "#Blog";

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        String authority = ("ADMIN");
        List<String> authorities = new ArrayList();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userDao.addUser(user);

        List<String> hashtags = new ArrayList();
        hashtags.add(hashtag2);
        hashtags.add(hashtag1);
        blog.setHashtags(hashtags);
        blog = dao.addBlog(blog, user);

        Blog blog2 = new Blog();
        blog2.setTitle("Second Blog Post");
        blog2.setContent("This is <i>more</i> important content");
        String hashtag = "#anotherpost";
        List<String> hashtags2 = new ArrayList();
        hashtags2.add(hashtag);
        blog2.setHashtags(hashtags2);
        blog2 = dao.addBlog(blog2, user);

        List<Blog> blogs = dao.getAllBlogs();
        assertEquals(2, blogs.size());
    }

    /**
     * Test of searchBlogs method, of class BlogSiteDao.
     */
    @Test
    public void testSearchBlogs() {
        Blog blog = new Blog();
        blog.setTitle("First Blog Post");
        blog.setContent("This is <b>A LOT</b> of important content");
        String hashtag1 = "post";
        String hashtag2 = "Blog";
        blog.setApprovedOn(LocalDate.now());

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        String authority = ("ADMIN");
        List<String> authorities = new ArrayList();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userDao.addUser(user);

        List<String> hashtags = new ArrayList();
        hashtags.add(hashtag2);
        hashtags.add(hashtag1);
        blog.setHashtags(hashtags);
        blog = dao.addBlog(blog, user);

        Blog blog2 = new Blog();
        blog2.setTitle("Second Blog Post");
        blog2.setContent("This is <i>more</i> important content");
        String hashtag = "post";
        List<String> hashtags2 = new ArrayList();
        hashtags2.add(hashtag);
        blog2.setHashtags(hashtags2);
        blog2.setApprovedOn(LocalDate.now());
        blog2 = dao.addBlog(blog2, user);

        Map<SearchTerm, String> criteria = new HashMap<>();
        criteria.put(SearchTerm.HASHTAG, "Blog");
        List<Blog> blogs = dao.searchBlogs(criteria, true);
        assertEquals(1, blogs.size());

        Map<SearchTerm, String> criteria2 = new HashMap<>();
        criteria2.put(SearchTerm.HASHTAG, "post");
        List<Blog> blogs2 = dao.searchBlogs(criteria2, true);
        assertEquals(2, blogs2.size());
    }

    @Test
    public void getNewestOldestBlogs() {
        Blog blog = new Blog();
        blog.setTitle("First Blog Post");
        blog.setContent("This is <b>A LOT</b> of important content");
        String hashtag1 = "post";
        String hashtag2 = "Blog";
        blog.setApprovedOn(LocalDate.now());

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        String authority = ("ADMIN");
        List<String> authorities = new ArrayList();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userDao.addUser(user);

        List<String> hashtags = new ArrayList();
        hashtags.add(hashtag2);
        hashtags.add(hashtag1);
        blog.setHashtags(hashtags);
        blog = dao.addBlog(blog, user);

        Blog blog2 = new Blog();
        blog2.setTitle("Second Blog Post");
        blog2.setContent("This is <i>more</i> important content");
        String hashtag = "post";
        List<String> hashtags2 = new ArrayList();
        hashtags2.add(hashtag);
        blog2.setHashtags(hashtags2);
        String localDateStr = "2019-10-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(localDateStr, formatter);
        blog2.setApprovedOn(dateTime);
        blog2 = dao.addBlog(blog2, user);
        
        List<Blog> recentBlogs = dao.getNewestBlogs(true);
        assertEquals(recentBlogs.get(0), blog);
        
        List<Blog> oldestBlogs = dao.getOldestBlogs(true);
        assertEquals(oldestBlogs.get(0), blog2);
    }

    /**
     * Test of addPicture method, of class BlogSiteDao.
     */
    @Test
    public void testAddPicture() {
    }

    /**
     * Test of deletePicture method, of class BlogSiteDao.
     */
    @Test
    public void testDeletePicture() {
    }

    /**
     * Test of getPictureById method, of class BlogSiteDao.
     */
    @Test
    public void testGetPictureById() {
    }

    @Test
    public void testAddGetPage() {
        Page page = new Page();
        page.setHeading("New page");
        page.setParagraph("Page information");
        page = dao.addPage(page);

        Page fromDao = dao.getPage(page.getPageId());
        assertEquals(page, fromDao);
    }

    @Test
    public void deletePage() {
        Page page = new Page();
        page.setHeading("New page");
        page.setParagraph("Page information");
        page = dao.addPage(page);

        dao.deletePage(page.getPageId());
        Page fromDao = dao.getPage(page.getPageId());
        assertNull(fromDao);
    }

    @Test
    public void editPage() {
        Page page = new Page();
        page.setHeading("New page");
        page.setParagraph("Page information");
        page = dao.addPage(page);

        Page fromDao = dao.getPage(page.getPageId());
        assertEquals(page, fromDao);

        page.setHeading("Different heading");
        dao.editPage(page);
        fromDao = dao.getPage(page.getPageId());
        assertEquals(page, fromDao);
    }

}
