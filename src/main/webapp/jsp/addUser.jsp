<%-- 
    Document   : addUser
    Created on : Sep 23, 2020, 5:01:34 PM
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
        <title>Add User</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">   
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">

    </head>
    <body>
        <div class="container">
            <div>
                <h1 class="col-lg-10">Company Blog</h1>
                <a class="col-lg-2" href="${pageContext.request.contextPath}/showLoginForm">Log in</a>
            </div>
            <div class="col-lg-12">
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
                            <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval</a></li>

                            <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                            <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                            </sec:authorize>
                    </ul>    
                </nav>

            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>
            <h1>Add User Form</h1>
            <form class="col-lg-6" method="POST" action="addUser">
                Username: <input class="form-control" type="text" 
                                 name="username" required/><br/>
                Password: <input class="form-control"  type="password" 
                                 name="password" required/><br/>
                Admin User? <input type="checkbox" 
                                   name="isAdmin" value="yes"/> <br/>
                <input class="btn btn-default" type="submit" value="Add User"/>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/displayUserList"> Cancel </a> <br/>
            </form>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
