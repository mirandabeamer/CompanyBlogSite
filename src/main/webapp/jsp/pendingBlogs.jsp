<%-- 
    Document   : ViewAllBlogs
    Created on : Sep 16, 2020, 5:30:21 PM
    Author     : mirandabeamer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View All Blogs</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">    
        <link href="${pageContext.request.contextPath}/css/blog.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h2>Company Blog</h2>
            <hr/>
            <nav class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <c:forEach var="currentPage" items="${pages}">
                        <li role="presentation"><a href="displayStaticPage?pageId=${currentPage.pageId}">${currentPage.heading}</a></li>
                        </c:forEach>
                        <sec:authorize access="isAuthenticated()">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayNewBlogForm">Add New Blog</a></li>
                        </sec:authorize>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/viewAllBlogs">View All Blogs
                            <sec:authorize access="isAuthenticated()">(User View)</sec:authorize></a></li>
                            <sec:authorize access="isAuthenticated()">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/viewAllBlogsAdmin">View All Blogs (Admin View)</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval</a></li>

                        <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                        </sec:authorize>
                </ul>    
            </nav>
            <form class="form-inline"> <h5 class="col-lg-1"> Sort By: </h5>
                <div class="form-group col-lg-2">
                    <input type="checkbox" class="form-control" id="sortByRecent">
                    <label for="sortByRecent">Most recent</label>
                </div>
                <div class="form-group col-lg-2">
                    <input type="checkbox" class="form-control" id="sortByOldest">
                    <label for="sortByOldest">Oldest</label>
                </div>
                <div class="form-group col-lg-3">
                    <label for="hashtag">
                        Hashtag: #
                    </label>
                    <input type="text" class="form-control mx-sm-3" id="search-term" name="search-term"/>
                </div>
                <button id="search-button" type="button">Sort</button>
            </form>
            <table class="col-lg-10" id="blog-table">
                <hr/>
                <c:forEach var="currentBlog" items="${blogs}">
                    <tr>
                        <th class="row"><h4 class="col-lg-8"> ${currentBlog.title} (Pending Approval)</h4>

                            <div class="col-lg-2">
                                <a href="reviewBlog?blogId=${currentBlog.blogId}">Review</a>
                            </div>

                        </th>
                    </tr>
                    <td><h4 class="col-lg-2">${currentBlog.approvedOn}</h4></td>
                    <tr><td>${currentBlog.content}</td></tr>
                    <td><hr/></td>
                    </c:forEach>
            </table>
            <a href="${pageContext.request.contextPath}/viewAllBlogs" type="button" id="undo-search" style="display:none" class="btn btn-default">Undo Search</a>

        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/searchBlog.js"></script>

    </body>
</html>
