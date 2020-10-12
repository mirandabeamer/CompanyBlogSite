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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mirandabeamer
 */
public class BlogSiteDaoDbImpl implements BlogSiteDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static String SQL_INSERT_BLOG
            = "insert into blog (title, content, expiration_date, display_date, approved_on) values (?, ?, ?, ?, ?)";

    private static String SQL_INSERT_PICTURE
            = "insert into picture (filename) values (?)";

    private static String SQL_INSERT_HASHTAG
            = "insert ignore into hashtag (hashtag) values(?)";

    private static String SQL_INSERT_PAGE
            = "insert into page (heading, content, picture_id) values(?, ?, ?)";

    private static String SQL_INSERT_BLOG_PICTURE
            = "insert into blog_picture (blog_id, picture_id) values (?, ?)";

    private static String SQL_INSERT_BLOG_HASHTAG
            = "insert into blog_hashtag (blog_id, hashtag_id) values (?, ?)";

    private static String SQL_INSERT_BLOG_USER
            = "insert into blog_user (blog_id, user_id) values(?, ?)";

    private static String SQL_DELETE_BLOG
            = "delete from blog where blog_id = ?";

    private static String SQL_DELETE_PICTURE
            = "delete from picture where picture_id = ?";

    private static String SQL_DELETE_PAGE
            = "delete from page where page_id = ?";

    private static String SQL_DELETE_BLOG_HASHTAG
            = "delete from blog_hashtag where blog_id = ?";

    private static String SQL_DELETE_BLOG_PICTURE
            = "delete from blog_picture where blog_id = ?";

    private static String SQL_DELETE_BLOG_USER
            = "delete from blog_user where blog_id = ?";

    private static String SQL_EDIT_BLOG
            = "update blog set title = ?, content = ?, expiration_date = ?, display_date =?, approved_on = ? where blog_id = ?";

    private static String SQL_EDIT_PAGE
            = "update page set heading = ?, content = ?, picture_id = ? where page_id = ?";

    private static String SQL_SELECT_BLOG
            = "select b.blog_id, title, content, expiration_date, display_date, approved_on from blog b "
            + "where b.blog_id = ?";

    private static String SQL_SELECT_PICTURE
            = "select picture_id, filename from picture where picture_id = ?";

    private static String SQL_SELECT_PAGE
            = "select page_id, heading, content, picture_id from page where page_id = ?";

    private static String SQL_SELECT_ALL_BLOGS
            = "select * from blog";

    private static String SQL_SELECT_ALL_PAGES
            = "select * from page";

    private static String SQL_SELECT_ALL_APPROVED_BLOGS
            = "select * from blog where approved_on IS NOT NULL";

    private static String SQL_SELECT_ALL_UNAPPROVED_BLOGS
            = "select * from blog where approved_on IS NULL";

    private static String SQL_SELECT_BLOGS_IN_ASCENDING_ORDER
            = "select b.blog_id, title, content, expiration_date, display_date, approved_on"
            + " from blog b where approved_on is not null order by approved_on asc";

    private static String SQL_SELECT_BLOGS_IN_DESCENDING_ORDER
            = "select b.blog_id, title, content, expiration_date, display_date, approved_on"
            + " from blog b where approved_on is not null order by approved_on desc";

    private static String SQL_SELECT_HASHTAG_ID_BY_HASHTAG
            = "select hashtag_id from hashtag where hashtag = ?";

    private static String SQL_SELECT_HASHTAG_BY_BLOG_ID
            = "select hashtag from hashtag h join blog_hashtag bh on bh.hashtag_id = h.hashtag_id where bh.blog_id = ?";

    private static String SQL_SELECT_PICTURE_BY_BLOG_ID
            = "select p.picture_id, filename from picture p join blog_picture bp on p.picture_id = bp.picture_id "
            + " where blog_id = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Blog addBlog(Blog blog, User user) {
        jdbcTemplate.update(SQL_INSERT_BLOG,
                blog.getTitle(),
                blog.getContent(),
                blog.getExpirationDate(),
                blog.getDisplayDate(),
                blog.getApprovedOn());
        int blogId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        blog.setBlogId(blogId);

        //add hashtags and insert into bridge table
        insertBlogHashtag(blog);
        //only inserting into bridge table - adding picture is separate method
        insertBlogPictures(blog);
        //insert into blog_user table 
        jdbcTemplate.update(SQL_INSERT_BLOG_USER, blogId, user.getId());
        return blog;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteBlog(int blogId) {
        jdbcTemplate.update(SQL_DELETE_BLOG_USER, blogId);
        jdbcTemplate.update(SQL_DELETE_BLOG_PICTURE, blogId);
        jdbcTemplate.update(SQL_DELETE_BLOG_HASHTAG, blogId);
        jdbcTemplate.update(SQL_DELETE_BLOG, blogId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void editBlog(Blog blog) {
        //edit blog
        jdbcTemplate.update(SQL_EDIT_BLOG,
                blog.getTitle(),
                blog.getContent(),
                blog.getExpirationDate(),
                blog.getDisplayDate(),
                blog.getApprovedOn(),
                blog.getBlogId());

        //delete from bridge table and update
        jdbcTemplate.update(SQL_DELETE_BLOG_HASHTAG, blog.getBlogId());
        insertBlogHashtag(blog);
        jdbcTemplate.update(SQL_DELETE_BLOG_PICTURE, blog.getBlogId());
        insertBlogPictures(blog);

    }

    @Override
    public Blog getBlog(int blogId) {
        Blog blog = new Blog();
        try {
            blog = jdbcTemplate.queryForObject(SQL_SELECT_BLOG, new BlogMapper(), blogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

        List<String> hashtags = findHashtagsForBlog(blog);
        blog.setHashtags(hashtags);
        List<Picture> pics = findPicturesForBlog(blog);
        blog.setPictures(pics);
        return blog;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = jdbcTemplate.query(SQL_SELECT_ALL_BLOGS, new BlogMapper());
        return associatePicturesAndHashtagsWithBlogs(blogs);
    }

    @Override
    public List<Blog> searchBlogs(Map<SearchTerm, String> criteria, boolean adminView) {
        if (criteria.isEmpty()) {
            return getAllBlogs();
        } else {

            //build prepared statement based on search
            StringBuilder sQuery = new StringBuilder("select b.blog_id, title, content, expiration_date, display_date, approved_on "
                    + "from blog b join blog_hashtag bh on b.blog_id = bh.blog_id "
                    + "join hashtag h on h.hashtag_id = bh.hashtag_id where ");
            if (!adminView) {
                sQuery.append(" approved_on is not null and ('");
                //only return blogs that are within display date and expiration date
                LocalDate now = LocalDate.now();
                sQuery.append(now);
                sQuery.append("' between display_date and expiration_date or expiration_date IS NULL) and ");
            }

            //build where clause 
            int numParams = criteria.size();
            int paramPosition = 0;

            //put positional parameter into array
            String[] paramVals = new String[numParams];
            Set<SearchTerm> keySet = criteria.keySet();
            Iterator<SearchTerm> iter = keySet.iterator();

            //build the where clause based on key/value pairs in the map
            while (iter.hasNext()) {
                SearchTerm currentKey = iter.next();
                //if it's not the first one in, will need "and" appended 
                if (paramPosition > 0) {
                    sQuery.append(" and ");
                }

                sQuery.append(currentKey);
                sQuery.append(" =?");

                //grab value for search criteria and put it into array
                paramVals[paramPosition] = criteria.get(currentKey);
                paramPosition++;
            }
            List<Blog> blogs = jdbcTemplate.query(sQuery.toString(), new BlogMapper(), paramVals);
            return associatePicturesAndHashtagsWithBlogs(blogs);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Picture addPicture(Picture picture) {
        jdbcTemplate.update(SQL_INSERT_PICTURE,
                picture.getFilename());
        int pictureId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        picture.setPictureId(pictureId);
        return picture;
    }

    @Override
    public void deletePicture(int pictureId) {
        jdbcTemplate.update(SQL_DELETE_PICTURE, pictureId);
    }

    @Override
    public Picture getPictureById(int pictureId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_PICTURE, new pictureMapper(), pictureId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Blog> getAllApprovedBlogs(int limit) {
        StringBuilder sQuery = new StringBuilder("select b.blog_id, title, content, expiration_date, display_date, approved_on "
                + "from blog b where approved_on IS NOT NULL and ('");
        LocalDate now = LocalDate.now();
        sQuery.append(now);
        sQuery.append("' between display_date and expiration_date or expiration_date IS NULL)");
        if(limit > 0){
            sQuery.append("limit " + limit);
        }
        List<Blog> blogs = jdbcTemplate.query(sQuery.toString(), new BlogMapper());
        return associatePicturesAndHashtagsWithBlogs(blogs);
    }

    @Override
    public List<Blog> getAllUnapprovedBlogs() {
        List<Blog> blogs = jdbcTemplate.query(SQL_SELECT_ALL_UNAPPROVED_BLOGS, new BlogMapper());
        return associatePicturesAndHashtagsWithBlogs(blogs);
    }

    @Override
    public List<Blog> getNewestBlogs(boolean adminView) {
        StringBuilder sQuery = new StringBuilder("select b.blog_id, title, content, expiration_date, display_date, approved_on from blog b ");

        if (adminView) {
            sQuery.append("order by approved_on desc");
            List<Blog> blogs = jdbcTemplate.query(sQuery.toString(), new BlogMapper());
            return associatePicturesAndHashtagsWithBlogs(blogs);
        }
        LocalDate now = LocalDate.now();
        sQuery.append("where approved_on is not null and ('" + now + "' between display_date and expiration_date or expiration_date IS NULL) order by approved_on desc");
        List<Blog> blogs = jdbcTemplate.query(sQuery.toString(), new BlogMapper());
        return associatePicturesAndHashtagsWithBlogs(blogs);

    }

    @Override
    public List<Blog> getOldestBlogs(boolean adminView) {
        StringBuilder sQuery = new StringBuilder("select b.blog_id, title, content, expiration_date, display_date, approved_on "
                + "from blog b ");
        if (adminView) {
            sQuery.append("order by approved_on asc");
            List<Blog> blogs = jdbcTemplate.query(sQuery.toString(), new BlogMapper());
            return associatePicturesAndHashtagsWithBlogs(blogs);
        }
        LocalDate now = LocalDate.now();
        sQuery.append(" where approved_on is not null and('" + now);
        sQuery.append("' between display_date and expiration_date or expiration_date IS NULL) order by approved_on asc");
        List<Blog> blogs = jdbcTemplate.query(sQuery.toString(), new BlogMapper());
        return associatePicturesAndHashtagsWithBlogs(blogs);
    }

    @Override
    public Page addPage(Page page) {
        Integer pictureId;
        try {
            pictureId = page.getPicture().getPictureId();
        } catch (NullPointerException e) {
            pictureId = null;
        }
        jdbcTemplate.update(SQL_INSERT_PAGE,
                page.getHeading(),
                page.getParagraph(),
                pictureId);
        int pageId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        page.setPageId(pageId);
        return page;
    }

    @Override
    public void deletePage(int pageId) {
        jdbcTemplate.update(SQL_DELETE_PAGE, pageId);
    }

    @Override
    public void editPage(Page page) {
        Integer pictureId;
        try {
            pictureId = page.getPicture().getPictureId();
        } catch (NullPointerException e) {
            pictureId = null;
        }
        jdbcTemplate.update(SQL_EDIT_PAGE,
                page.getHeading(),
                page.getParagraph(),
                pictureId,
                page.getPageId());
    }

    @Override
    public Page getPage(int pageId) {
        Page page = new Page();
        try {
            page = jdbcTemplate.queryForObject(SQL_SELECT_PAGE, new pageMapper(), pageId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        try {
            Picture picture = jdbcTemplate.queryForObject(SQL_SELECT_PICTURE, new pictureMapper(), page.getPicture().getPictureId());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            page.setPicture(null);
        }
        return page;
    }

    @Override
    public List<Page> getAllPages() {
        return jdbcTemplate.query(SQL_SELECT_ALL_PAGES, new pageMapper());
    }

    private static final class BlogMapper implements RowMapper<Blog> {

        @Override
        public Blog mapRow(ResultSet rs, int i) throws SQLException {
            Blog blog = new Blog();
            blog.setBlogId(rs.getInt("blog_id"));
            blog.setTitle(rs.getString("title"));
            blog.setContent(rs.getString("content"));
            try {
                blog.setDisplayDate(rs.getDate("display_date").toLocalDate());
            } catch (NullPointerException e) {
                blog.setDisplayDate(null);
            }
            try {
                blog.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
            } catch (NullPointerException e) {
                blog.setExpirationDate(null);
            }
            try {
                blog.setApprovedOn(rs.getDate("approved_on").toLocalDate());
            } catch (NullPointerException e) {
                blog.setApprovedOn(null);
            }
            return blog;
        }
    }

    private static final class hashtagMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getString("hashtag");
        }

    }

    private static final class pictureMapper implements RowMapper<Picture> {

        @Override
        public Picture mapRow(ResultSet rs, int i) throws SQLException {
            Picture pic = new Picture();
            pic.setPictureId(rs.getInt("picture_id"));
            pic.setFilename(rs.getString("filename"));
            return pic;
        }

    }

    private static final class pageMapper implements RowMapper<Page> {

        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException {
            Page page = new Page();
            page.setPageId(rs.getInt("page_id"));
            page.setHeading(rs.getString("heading"));
            page.setParagraph(rs.getString("content"));
            Picture pic = new Picture();
            pic.setPictureId(rs.getInt("picture_id"));
            page.setPicture(pic);
            return page;
        }

    }

//HELPER METHODS
    private List<String> findHashtagsForBlog(Blog blog) {
        List<String> hashtags = jdbcTemplate.query(SQL_SELECT_HASHTAG_BY_BLOG_ID, new hashtagMapper(), blog.getBlogId());
        if (hashtags.isEmpty()) {
            return null;
        }
        return hashtags;
    }

    private List<Picture> findPicturesForBlog(Blog blog) {
        List<Picture> pics = jdbcTemplate.query(SQL_SELECT_PICTURE_BY_BLOG_ID, new pictureMapper(), blog.getBlogId());
        if (pics.isEmpty()) {
            return null;
        }
        return pics;
    }

    private void insertBlogHashtag(Blog blog) {
        List<String> hashtags = blog.getHashtags();
        try {
            for (String hashtag : hashtags) {
                jdbcTemplate.update(SQL_INSERT_HASHTAG, hashtag);
                int hashtagId = jdbcTemplate.queryForObject(SQL_SELECT_HASHTAG_ID_BY_HASHTAG, Integer.class, hashtag);
                //update hashtag/blog bridge table for each hashtag with current blog
                jdbcTemplate.update(SQL_INSERT_BLOG_HASHTAG, blog.getBlogId(), hashtagId);
            }
        } catch (NullPointerException e) {
            //
        }

    }

    private void insertBlogPictures(Blog blog) {
        List<Picture> pictures = blog.getPictures();
        if (pictures != null) {
            for (Picture picture : pictures) {
                jdbcTemplate.update(SQL_INSERT_BLOG_PICTURE, blog.getBlogId(), picture.getPictureId());
            }
        }
    }

    private List<Blog> associatePicturesAndHashtagsWithBlogs(List<Blog> blogs) {
        for (Blog blog : blogs) {
            List<String> hashtags = findHashtagsForBlog(blog);
            blog.setHashtags(hashtags);
            List<Picture> pics = findPicturesForBlog(blog);
            blog.setPictures(pics);
        }
        return blogs;
    }
}
