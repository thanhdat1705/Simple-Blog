<%-- 
    Document   : postArticle
    Created on : 27 Sep, 2021, 7:09:17 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <title>Post Article Page</title>
        <script>
            $(function () {
                $("#postArticleForm").validate({
                    rules: {
                        txtTittle: {
                            required: true
                        },
                        txtShortDes: {
                            required: true
                        },
                        txtContent: {
                            required: true
                        }
                    }
                })
            });
        </script>
        <style>
            #postArticleForm label.error {
                color: red;
                padding-left: 20px;
                font-style: italic;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <div>
                <form action="logoutAction">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <c:redirect url="searchArticleAction"/>
                            </c:if>
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                                <a href="searchArticleAction">Back to Article Page</a>
                            </div>
                            <div class="welcome-content">
                                <input type="submit" value="Logout" name="btnAction" />
                            </div>
                        </c:if>
                        <c:if test="${empty wellcomeUser}">
                            <c:redirect url="searchArticleAction"/>
                        </c:if>
                    </div>
                </form>
            </div>
            <div class="container">
                <c:set var="error" value="${requestScope.DUP_TITTLE}"/>
                <div style="border: solid black 1px;">
                    <h2>Post Article</h2>
                    <form action="postArticleAction" method="POST" id="postArticleForm">
                        <div class="create-container">
                            <div class="create-content">
                                <label>Tittle:</label>  
                                <input style="margin-right: 10px" type="text" name="txtTittle" value="${param.txtTittle}"/>
                                <c:if test="${not empty error}">
                                    <font color="red">
                                    ${error}
                                    </font>
                                </c:if>
                                <br/>
                            </div>
                            <div class="create-content">
                                <label>Short Description:</label> 
                                <input type="text" name="txtShortDes" value="${param.txtShortDes}"/><br/>
                            </div>
                            <div class="create-content">
                                <label>Content:</label> 
                                <textarea rows="5" cols="80" name="txtContent">${param.txtContent}</textarea><br/>
                            </div>
                        </div>
                        <input style="margin-left: 20px; width: 6%;" type="submit" value="Post" name="btnAction"/>
                        <input style="margin-left: 20px; width: 5%;" type="reset" value="Reset" />
                    </form><br/>
                </div>
            </div>
        </div>
    </body>
</html>
