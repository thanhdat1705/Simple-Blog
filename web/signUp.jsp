<%-- 
    Document   : signUp
    Created on : Sep 15, 2021, 11:56:29 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#signUpForm").validate({
                    rules: {
                        txtName: {
                            required: true,
                            rangelength: [3, 50]
                        },
                        txtEmail: {
                            required: true,
                            email: true
                        },
                        txtPassword: {
                            required: true,
                            rangelength: [3, 16]
                        },
                        txtConfirm: {
                            equalTo: "#txtPassword"
                        }
                    }
                })
            });
        </script>
        <style>
            #signUpForm label.error {
                color: red;
                padding-left: 20px;
                font-style: italic;
            }
        </style>
        <title>Sign Up</title>
    </head>
    <body>
        <div class="authen-layout">
            <div class="sidenav">
                <div class="login-main-text">
                    <h1>Simple Blog<br> <span style="font-size: 25px">Sign Up Page</span></h1>
                    <p>Login or register from here to access.</p>
                </div>
            </div>
            <div class="main">
                <div class="login-form">
                    <c:set var="error" value="${requestScope.IS_DUP_EMAIL}"/>
                    <a href="searchArticleAction">Go back to Article Page</a><br>
                    <form action="signUpAction" method="POST" id="signUpForm">
                        <div class="form-group">
                            <label>Email</label><br>
                            <input type="text" name="txtEmail" value="${param.txtEmail}" class="form-control" placeholder="Email">
                        </div>
                        <div class="form-group">
                            <label>Password</label><br>
                            <input type="password" name="txtPassword" id="txtPassword" value="" class="form-control" placeholder="Password">
                        </div>
                        <div class="form-group">
                            <label>Confirm password</label><br>
                            <input type="password" name="txtConfirm" value="" class="form-control" placeholder="Confirm Password">
                        </div>
                        <div class="form-group">
                            <label>Full Name</label><br>
                            <input type="text" name="txtName" value="${param.txtName}" class="form-control" placeholder="Full Name">
                        </div>
                        <c:if test="${not empty error}">
                            <font color="red">
                            ${error}
                            </font><br/>
                        </c:if>
                        <input type="submit" value="Sign Up" name="btnAction"/>
                        <input type="reset" value="Reset"/>
                    </form> 
                    <a href="loginPageStatic">Login</a>
                </div>
            </div>
        </div>
    </body>
</html>
