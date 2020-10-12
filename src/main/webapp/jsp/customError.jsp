<%-- 
    Document   : login
    Created on : Sep 22, 2020, 12:52:08 PM
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
        <title>Error Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">    
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">
    </head>
    <body>
        <div class="container">
            <h1>Company Blog</h1>
            <hr/>
            <nav class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <c:forEach var="currentPage" items="${pages}">
                        <li role="presentation"><a href="displayStaticPage?pageId=${currentPage.pageId}">${currentPage.heading}</a></li>
                        </c:forEach>
                        <sec:authorize access="isAuthenticated()">
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/displayNewBlogForm">Add New Blog</a></li>
                        </sec:authorize>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/viewAllBlogs">View All Blogs
                            <sec:authorize access="isAuthenticated()">(User View)</sec:authorize></a></li>
                            <sec:authorize access="isAuthenticated()">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/viewAllBlogsAdmin">View All Blogs (Admin View)</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval</a></li>

                        <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                        </sec:authorize>
                </ul>    
            </nav>
            <div>
                <h3>An error has occurred.</h3>
                <h5>${errorMessage}</h5>
            </div>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>