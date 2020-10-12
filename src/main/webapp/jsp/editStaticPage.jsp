<%-- 
    Document   : AddBlog
    Created on : Sep 16, 2020, 5:30:03 PM
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
            <h2>Edit ${page.heading}</h2>
            <hr/>
            <p id="errorMessage" value="${errorMessage}">${errorMessage}</p>
            <sf:form class="col-lg-10" id="editPageForm" role="form" modelAttribute="page" method="POST" action="editPage">
                <div class="form-group row col-lg-12">
                    <label for="heading">Page Heading:</label>
                    <sf:input class="form-control" type="text" name="heading" id="heading" path="heading"/>
                    <sf:errors path="heading" cssclass="error"></sf:errors>


                    </div>
                    <div class="form-group row col-lg-12">
                        <label for="textarea">Body: </label>
                    <sf:textarea form="editPageForm" path="paragraph"  class="form-control" id="textarea" name="textarea" type="text"/>
                    <sf:errors path="paragraph" cssclass="error"></sf:errors>
                    </div>
                    <br/>
                    <div class="form-group">
                        <input type="submit" class="btn btn-default" value="Submit Page"/>
                        <a class="btn btn-default" href="${pageContext.request.contextPath}/displayPageManagement">Cancel</a>
                    <sf:hidden path="pageId"/>
                </div>
            </sf:form>


        </div>
        <script src="https://cdn.tiny.cloud/1/df16eym8yp7wsete4rn1cbqwk9h4ewhpzl97aherjjhzz3ek/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/blog.js"></script>

    </body>
</html>
