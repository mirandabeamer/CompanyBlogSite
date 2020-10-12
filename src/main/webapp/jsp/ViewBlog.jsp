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
        <title>View Blog</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">
    </head>
    <body>
        <div class="container">
            <div>
                <h2>Company Blog</h2>
            </div>
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
                        <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval 
                            <c:if test= "${numPendingApprovals} !=null">(${numPendingApprovals})</c:if></a></li>

                        <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                        </sec:authorize>
                </ul>    
            </nav>
            <div>
                <div class="col-lg-8">
                    <h1>${blog.title}</h1><p> Written by <c:out value ="${blog.author.username}"/></p>
                    <sec:authorize access="isAuthenticated()">
                        <h5>${approvalStatus}</h5>
                    </sec:authorize>
                    <h5>${blog.approvedOn.month} ${blog.approvedOn.dayOfMonth} ${blog.approvedOn.year}</h5>
                </div>
                <div class="col-lg-3">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <c:if test="${empty blog.approvedOn}">
                            <a href="approveBlog?blogId=${blog.blogId}" class="btn btn-default">Approve</a> 
                        </c:if>
                        <a href="DisplayEditBlog?blogId=${blog.blogId}" class="btn btn-default">Edit</a> 
                        <a href="deleteBlog?blogId=${blog.blogId}" class="btn btn-default">Delete</a>
                    </sec:authorize>
                </div>
            </div>

            <div id="blog-content" class="col-lg-10"><p>
                    <c:forEach var="hashtag" items="${blog.hashtags}">
                        <c:if test="${not empty hashtag}">
                            #${hashtag} 
                        </c:if>
                    </c:forEach></p>
                <hr/>
                <p>${blog.content}</p>
            </div>
            <sec:authorize access="isAuthenticated()">
                <div id="dates" class="col-lg-10">
                    <hr/>
                    <ul>
                        <li>Display Date: <c:out value="${blog.displayDate}"/></li>
                        <li>Expiration Date <c:out value = "${blog.expirationDate}"/></li>
                    </ul>
                </div>
            </sec:authorize>


        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/blog.js"></script>

    </body>
</html>
