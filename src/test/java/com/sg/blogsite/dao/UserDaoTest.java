/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.dao;

import com.sg.blogsite.model.Blog;
import com.sg.blogsite.model.User;
import java.util.ArrayList;
import java.util.List;
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
public class UserDaoTest {

    private UserDao dao;

    public UserDaoTest() {
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
        dao = ctx.getBean("userDao", UserDao.class);

        List<User> users = dao.getAllUsers();
        for (User user : users) {
            dao.deleteUser(user.getUsername());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class UserDao.
     */
    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("User");
        String authority = "USER";
        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        dao.addUser(user);
        User fromDao = dao.getUser(user.getUsername());
        assertEquals(user, fromDao);
    }

    /**
     * Test of deleteUser method, of class UserDao.
     */
    @Test
    public void testDeleteUser() {

        User user = new User();
        user.setUsername("User");
        user.setPassword("User");
        String authority = "USER";
        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        dao.addUser(user);
        User fromDao = dao.getUser(user.getUsername());
        assertEquals(user, fromDao);

        dao.deleteUser(user.getUsername());
        fromDao = dao.getUser(user.getUsername());
        assertNull(fromDao);
    }

    /**
     * Test of getAllUsers method, of class UserDao.
     */
    @Test
    public void testGetAllUsers() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("User");
        String authority = "USER";
        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        dao.addUser(user);

        User user2 = new User();
        user2.setUsername("Admin");
        user2.setPassword("Admin");
        user2.setAuthorities(authorities);
        dao.addUser(user2);

        List<User> users = dao.getAllUsers();
        assertEquals(2, users.size());
    }

}
