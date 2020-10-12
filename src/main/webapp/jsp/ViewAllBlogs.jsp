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
                        <li role="presentation" id="userView"><a href="${pageContext.request.contextPath}/viewAllBlogsAdmin" id="userViewButton">View All Blogs (Admin View)</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval 
                                <c:if test= "${numPendingApprovals} !=null">(${numPendingApprovals})</c:if></a></li>

                            <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                        </sec:authorize>
                </ul>    
            </nav>
            <h4><c:if test="${adminView}">ADMIN VIEW </c:if></h4>
            <p id="errorMessage">${errorMessage}</p>
            <div class="row col-lg-12">
                <h5 class="col-lg-2"> Sort By: </h5>
                <div class="form-check col-lg-2">
                    <input type="radio" class="form-check-input" id="sortByRecent" value="recent" name="sortBy">
                    <label for="sortByRecent">Most recent</label>
                </div>
                <div class="form-check col-lg-2">
                    <input type="radio" class="form-check-input" id="sortByOldest" value="oldest" name="sortBy">
                    <label for="sortByOldest">Oldest</label>
                </div>
                <div class="form-group col-lg-4">
                    <input type="radio" class="form-check-input" id="hashtag" value="hashtag" name="sortBy">
                    <label for="hashtag">
                        Hashtag: #
                    </label>
                    <input type="text" class="form-control col-lg-2" id="search-term" name="search-term"/>
                </div>
                <input id="search-button" class="btn btn-default" type="submit" value="Sort"/>
            </div>
            <table class="col-lg-10" id="blog-table">
                <c:forEach var="currentBlog" items="${blogs}">
                    <tr>
                        <th class="row"><h4 class="col-lg-8"> ${currentBlog.title}
                                <sec:authorize access="hasRole('ROLE_USER')">
                                    <c:if test="${currentBlog.approvedOn == null}">(Pending Approval) </c:if>
                                    <c:if test="${currentBlog.expirationDate < today}"> (Expired)</c:if>
                                    <c:if test="${currentBlog.displayDate > today}">(Will display <c:out value="${currentBlog.displayDate}"/>)
                                    </c:if>
                                </sec:authorize></h4>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <div class="col-lg-2">
                                    <a href="DisplayEditBlog?blogId=${currentBlog.blogId}">Edit</a> | 
                                    <a href="deleteBlog?blogId=${currentBlog.blogId}" onclick="deleteBlog(${currentBlog.blogId})">Delete</a>
                                </div>
                            </sec:authorize>
                        </th>
                    </tr>
                    <tr><td>${currentBlog.content}</td></tr>
                    <tr><td>
                            <a href="viewBlog?blogId=${currentBlog.blogId}">Read More</a>
                        </td></tr>
                    <td><hr/></td>
                    </c:forEach>
            </table>
            <a href="${pageContext.request.contextPath}/viewAllBlogs" type="button" id="undo-search" style="display:none" class="btn btn-default">Undo Search</a>
            <a href="${pageContext.request.contextPath}/viewAllBlogsAdmin" type="button" id="undo-search-admin" style="display:none" class="btn btn-default">Undo Search</a>
            <input style="display:none" name="username" id="username" value="${adminView}"/>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/searchBlog.js"></script>

    </body>
</html>
