<%-- 
    Document   : EditBlog
    Created on : Sep 22, 2020, 8:39:45 AM
    Author     : mirandabeamer
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Blog</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">
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
                        <li role="presentation"><a href="${pageContext.request.contextPath}/approveBlogs">Blogs Pending Approval 
                                <c:if test= "${numPendingApprovals} !=null">(${numPendingApprovals})</c:if></a></li>

                        <li role="presentation"> <a href="${pageContext.request.contextPath}/displayUserList"> User Admin </a> </li>   
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayPageManagement">Static page management</a></li>
                        </sec:authorize>
                </ul>    
            </nav>
            <h1>Edit ${blog.title}</h1>
            <p>${errorMessage}</p>
            <sf:form class="form-horizontal col-lg-10" role="form" modelAttribute="blog" action="editBlog" method="POST" id="editBlogForm">
                <div class="form-group row col-lg-12">
                    <label for="edit-title">Title:</label>
                    <div>
                        <sf:input class="form-control" type="text" name="title" id="edit-title" path="title" placeholder="Title" required="required"/>
                        <sf:errors path="title" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group row col-lg-5">
                        <label for="displayDate">Display Date: </label>
                        <div>
                            <input class="form-control" name="dispDate" type="date" value="${displayDateString}" id="displayDate"/>
                    </div>
                </div>
                <div class="form-group row col-lg-5">
                    <label for="expirationDate">Expiration Date:</label>
                    <div>
                        <input class="form-control" name="expDate" type="date" value="${expirationDateString}" id="expirationDate"/>
                    </div>
                </div>
                <div class="row form-group col-lg-12">
                    <label for="hashtags">Hashtags: </label>
                    <div>
                        <sf:input class="form-control" path="hashtags" value="${hashtagString}" type="text" name="hashtags" id="hashtags" placeholder="#Hashtags, #Categories"/>
                    </div>
                </div>
                <div class="form-group row col-lg-12">
                    <label for="textarea">Content: </label>
                    <div>
                        <sf:textarea form="editBlogForm" id="textarea" name="textarea" type="text" path="content"/>
                        <sf:errors path="content" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group row col-lg-12">
                        <input type="submit" class="btn btn-default"value="Edit Blog"/>
                        <a class="btn btn-default" href="${pageContext.request.contextPath}/">Cancel</a>
                </div>
                <sf:hidden path="blogId"/>
                <sf:hidden path="approvedOn"/>
                <sf:hidden path="displayDate"/>
                <sf:hidden path="expirationDate"/>
            </sf:form>



        </div>
        <script src="https://cdn.tiny.cloud/1/df16eym8yp7wsete4rn1cbqwk9h4ewhpzl97aherjjhzz3ek/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/blog.js"></script>

    </body>
</html>