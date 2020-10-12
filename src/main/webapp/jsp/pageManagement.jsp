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
        <title>Static page management</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">    
        <link href="${pageContext.request.contextPath}/css/blog.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h2>Company Blog</h2>
            <hr/>
            <nav class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/">Home</a></li>
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
                        <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval</a></li>

                        <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                        </sec:authorize>
                </ul>    
            </nav>
            <p>${errorMessage}</p>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/displayAddNewPage">Add New Page</a>

            <table class="col-lg-10 table table-striped" id="page-table">
                <hr/>
                <th>Page Heading</th>
                <th></th>
                    <c:forEach var="currentPage" items="${pages}">
                    <tr>
                        <td><a href="displayStaticPage?pageId=${currentPage.pageId}">${currentPage.heading}</a></td>
                        <td><a href="displayEditStaticPage?pageId=${currentPage.pageId}">Edit</a> | <a href="deletePage?pageId=${currentPage.pageId}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <a href="${pageContext.request.contextPath}/viewAllBlogs" type="button" id="undo-search" style="display:none" class="btn btn-default">Undo Search</a>

        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/searchBlog.js"></script>

    </body>
</html>
