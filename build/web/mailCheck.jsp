<%-- 
    Document   : mailCheck
    Created on : 27 Sep, 2021, 5:03:06 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <title>Verify Account</title>
    </head>
    <body>
        <div class="authen-layout">
            <c:set var="verifyCode" value="${requestScope.VERIFYCODE}"/>
            <c:set var="email" value="${requestScope.EMAIL}"/>
            <c:set var="pass" value="${requestScope.PASSWORD}"/>
            <div class="sidenav">
                <div class="login-main-text">
                    <h1>Simple Blog<br> <span style="font-size: 25px">Verify your Blog Account</span></h1>
                    <p>Welcome ${mail} Enter verify code we sent in your mail to active your Account.</p>
                </div>
            </div>
            <div class="main">
                <div>
                    <form action="verifyProccess">
                        Verify Code <input type="text" name="txtVerify" value="" /><br>
                        <input type="hidden" name="txtVerifyCode" value="${verifyCode}" />
                        <input type="hidden" name="txtEmail" value="${email}" />
                        <input type="hidden" name="txtPassword" value="${pass}" />
                        <c:set var="errors" value="${requestScope.ERROR}" />
                        <font color="red"><br>
                        <c:if test="${not empty errors.invalidVerifyCode}">
                            ${errors.invalidVerifyCode}<br>
                        </c:if>
                        </font><br>
                        <input type="submit" name="btnAction" value="Verify" />
                        <input type="submit" name="btnAction" value="Cancel" />

                        <br>Can not receive verify code? <input type="submit" name="btnAction" value="Click here to re-send verify code" /> 
                    </form><br>
                </div>
            </div>
        </div>
    </body>
</html>
