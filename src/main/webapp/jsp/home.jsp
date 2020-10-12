<%-- 
    Document   : home
    Created on : Sep 11, 2020, 3:26:59 PM
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
        <title>Home page</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">
    </head>
    <body>
        <div class="container" id="fillscreen">
            <div id="header" class="col-lg-12">
                <div>
                    <h1 class="col-lg-10">Company Blog</h1>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <a class="col-lg-2 btn btn-default" href="${pageContext.request.contextPath}/showLoginForm">Log in</a>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <h5>Hello : ${pageContext.request.userPrincipal.name} | 
                            <a href="<c:url value="/j_spring_security_logout" />" > Logout</a></h5>
                        </c:if>
                </div>
                <div class="col-lg-12">
                    <nav class="navbar">
                        <ul class="nav nav-tabs">
                            <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
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
                                <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval (${numPendingApprovals})</a></li>

                                <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                                <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                                </sec:authorize>
                        </ul>    
                    </nav>
                </div>
            </div>
            <div class="col-lg-5">
                <h3>About: </h3>
                <p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
                    fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                    culpa qui officia deserunt mollit anim id est laborum."</p>

                <p>in voluptate velit esse cillum dolore eu
                    fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                    culpa qui officia deserunt mollit anim id est laborum</p>
            </div>
            <div class="col-lg-6">
                <img id="picture" src ="${pageContext.request.contextPath}/images/blogPic.jpg"/>
            </div>
            <div class="col-lg-12">
                <hr/>
                <div class="row">
                    <h3 class="float-left">Recent blogs: </h3> 
                </div> 
                <c:forEach var="currentBlog" items="${blogs}">
                    <table class="table">
                        <tr>
                            <td class="col-lg-3">
                                <h5> ${currentBlog.title}</h5> 
                            </td>
                            <td class="col-lg-2">
                                By <c:out value="${currentBlog.author.username}"/>
                            </td>
                            <td class="col-lg-3">
                                <c:forEach var="hashtag" items="${currentBlog.hashtags}">
                                    <c:if test="${not empty hashtag}">
                                        #<c:out value="${hashtag}"/>
                                    </c:if>

                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </c:forEach>
            </div>
        </div>
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
