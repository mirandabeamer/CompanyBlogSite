<%-- 
    Document   : AddBlog
    Created on : Sep 16, 2020, 5:30:03 PM
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
        <title>Add New Blog</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">
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
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/displayNewBlogForm">Add New Blog</a></li>
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
            <h2>Add New Blog</h2>
            <hr/>
            <p id="errorMessage" value="${errorMessage}">${errorMessage}</p>
            <form class="col-lg-10" id="addBlogForm" role="form" method="POST" action="addBlog">
                <div class="form-group row col-lg-12">
                    <label for="title">Blog Title:</label><input class="form-control" type="text" name="title" id="title" placeholder="Title" required/>
                </div>
                <div class="row form-group">
                    <div class="col-lg-5">
                        <label for="displayDate">Display Date: </label><input class="form-control" type="date" name="displayDate" id="displayDate"/>
                    </div>
                    <div class="col-lg-5">
                        <label for="expirationDate">Expiration Date:</label><input class="form-control" type="date" name="expirationDate" id="expirationDate"/>
                    </div>

                </div>
                <div class="row form-group col-lg-12">
                    <label for="hashtags">Hashtags: </label>
                    <div>
                        <input class="form-control" type="text" name="hashtags" id="hashtags" placeholder="#Hashtags, #Categories"/>
                    </div>
                </div>
                <div class="form-group row col-lg-12">
                    <label for="textarea">Content: </label>
                    <textarea form="addBlogForm" class="form-control" id="textarea" name="textarea" type="text"></textarea>
                </div>
                <br/>
                <div class="form-group">
                    <input style="display:none" name="username" value="${pageContext.request.userPrincipal.name}"/>
                    <input type="submit" class="btn btn-default" value="Submit Blog"/>
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/">Cancel</a>
                </div>
            </form>


        </div>
        <script src="https://cdn.tiny.cloud/1/df16eym8yp7wsete4rn1cbqwk9h4ewhpzl97aherjjhzz3ek/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/blog.js"></script>

    </body>
</html>
