/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogsite.dao;

import com.sg.blogsite.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mirandabeamer
 */
public class UserDaoDbImpl implements UserDao {

    private static final String SQL_INSERT_USER
            = "insert into user(username, password, enabled) values(?, ?, 1)";
    private static final String SQL_INSERT_AUTHORITY
            = "insert into authority(username, authority) values(?, ?)";
    private static final String SQL_DELETE_USER
            = "delete from user where username=?";
    private static final String SQL_DELETE_AUTHORITIES
            = "delete from authority where username = ?";
    private static final String SQL_DELETE_BLOG_USER
            = "delete from blog_user where user_id=?";
    private static final String SQL_GET_ALL_USERS
            = "select * from user";
    private static final String SQL_GET_USER
            = "select user_id, username, password, enabled from user where username = ?";
    private static final String SQL_SELECT_AUTHORITIES_BY_USERNAME
            = "select authority from authority where username = ?";
    private static final String SQL_SELECT_USER_FOR_BLOG
            = "select u.user_id, u.username, u.password, u.enabled from user u "
            + "join blog_user bu on u.user_id = bu.user_id where blog_id = ? ";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User addUser(User newUser) {
        jdbcTemplate.update(SQL_INSERT_USER,
                newUser.getUsername(), newUser.getPassword());
        newUser.setId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

        //insert user's roles
        List<String> authorities = newUser.getAuthorities();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    newUser.getUsername(),
                    authority);
        }
        return newUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteUser(String username) {
        User user = jdbcTemplate.queryForObject(SQL_GET_USER, new UserMapper(), username);
        jdbcTemplate.update(SQL_DELETE_BLOG_USER, user.getId());
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, username);
        jdbcTemplate.update(SQL_DELETE_USER, username);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public User getUser(String username) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_GET_USER, new UserMapper(), username);
            List<String> authorities = associateUserWithAuthority(username);
            user.setAuthorities(authorities);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User getUserForBlog(int blogId) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_USER_FOR_BLOG, new UserMapper(), blogId);
            List<String> authorities = associateUserWithAuthority(user.getUsername());
            user.setAuthorities(authorities);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }

    }

    private static final class authorityMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getString("authority");
        }

    }

    private List<String> associateUserWithAuthority(String username) {
        List<String> authorities = jdbcTemplate.query(SQL_SELECT_AUTHORITIES_BY_USERNAME, new authorityMapper(), username);
        return authorities;
    }
}
