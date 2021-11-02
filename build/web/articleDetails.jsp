<%-- 
    Document   : articleDetails
    Created on : 29 Sep, 2021, 11:05:09 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="js/notification.js" type="text/javascript">
        <link rel="stylesheet" href="js/common.js" type="text/javascript">
        <title>Detail Page</title>
    </head>
    <body onload="checkSession()">
        <div class="wrapper">
            <div>
                <form action="logoutAction">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                                <c:if test="${wellcomeUser.roleId == 1}">
                                    <a href="searchArticleAction">Back to Management Page</a>
                                </c:if>
                                <c:if test="${wellcomeUser.roleId != 1}">
                                    <a href="checkLoginAction?btnAction=Post">Post Article</a>
                                    <a style="margin-left: 20px" href="searchArticleAction">Back to Article Page</a>
                                </c:if>
                            </div>
                            <div class="welcome-content">
                                <input type="submit" value="Logout" name="btnAction" />
                            </div>
                        </c:if>
                        <c:if test="${empty wellcomeUser}">
                            <div>
                                <h3>Welcome, Quest.</h3>
                                <a href="checkLoginAction?btnAction=Post">Post Article</a>
                                <a style="margin-left: 20px" href="searchArticleAction">Back to Article Page</a>
                            </div>
                            <div class="welcome-content">
                                <a href="loginPageStatic">Login</a>
                                <a href="signUpPageStatic">Sign Up</a>
                            </div>
                        </c:if>
                    </div>
                </form>
            </div>
            <div style="border: solid black 1px;">
                <c:set var="article" value="${requestScope.VIEWRESULT}" />
                <c:set var="listCmt" value="${requestScope.LIST_COMMENT}" />
                <c:set var="totalCmt" value="${requestScope.TOTAL_COMMENT}" />
                <c:set var= "role" value="${requestScope.USERROLE}"/>
                <c:if test="${not empty article}">
                    <div class="detail-container">
                        <div style="display: flex; flex-direction: column; background-color: #fafaf0; border-bottom: 1px solid #e0e0e0">
                            <span style="font-size: 13px; font-style: italic; margin-top: 10px">
                                <div style="display: flex">
                                    ${article.author} - ${article.time} - ${article.createDate} - <img style="margin-left: 4px; margin-right: 4px" width="15" src="icon/chat.svg"/> ${article.totalComment}
                                </div>
                            </span>
                            <h2>${article.tittle}</h2>
                            <p>${article.shortDescription}</p>
                            <p>${article.content}</p>
                        </div>
                        <div>
                            <p style="font-size: 18px; font-weight: 600;">Comment (${article.totalComment}):</p>
                            <c:if test="${wellcomeUser.roleId != 1}">
                                <form action="checkLoginAction" method="POST">
                                    <div class="comment">
                                        <textarea rows="4" cols="60" name="txtComment" placeholder="Share your thoughts.">${param.txtContent}</textarea>
                                        <input type="hidden" id="hdnSession" value="${wellcomeUser}" />
                                        <input type="hidden" name="pk" value="${article.id}"/>
                                        <input type="hidden" name="txtStatus" value="Active"/>
                                        <input type="button" id="commentButton" value="Comment" name="btnAction" onclick="popupCommentWindow('commentWindow')"/>
                                        <div id="warningMessage">
                                            <div id="warningMessage_content">
                                                <div id="commentWindow">
                                                    <h2>Sign in to comment</h2><br>
                                                    <div>
                                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeCommentWindow('commentWindow')">
                                                        <input class="confirm" type="submit" name="btnAction" value="Login">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>  
                                    </div>
                                </form>
                            </c:if>
                            <c:if test="${not empty listCmt}">
                                <c:forEach var="cmt" items="${listCmt}" varStatus="counter">
                                    <c:if test="${cmt.userComment == 'You'}">
                                        <p><span style="font-weight: 600; margin-right: 10px; color: blue">${cmt.userComment}</span>${cmt.content}</p>
                                        </c:if>
                                        <c:if test="${cmt.userComment != 'You'}">
                                        <p><span style="font-weight: 600; margin-right: 10px">${cmt.userComment}</span>${cmt.content}</p>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty listCmt}">
                                <p>Be the first to comment on this article!</p>
                            </c:if>
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <div style="border-top: 1px solid #e0e0e0">
                                    <form action="updateArticleStatusAction" method="POST">
                                        <h4>Status: ${article.status}</h4>
                                        <input type="hidden" name="pk" value="${article.id}">
                                        <c:if test="${article.status == 'Deleted'}">
                                            <input style="margin-left: 50px; width: 7%;" type="submit" value="Restore" name="btnAction" />
                                        </c:if>
                                        <c:if test="${article.status == 'Active'}">
                                            <input style="margin-left: 50px; width: 7%;" type="submit" value="Delete" name="btnAction" />
                                        </c:if>
                                        <c:if test="${article.status == 'New'}">
                                            <input style="margin-left: 50px; width: 7%;" type="submit" value="Approval" name="btnAction" />
                                            &ensp;
                                            <input style="margin-left: 50px; width: 7%;" type="submit" value="Delete" name="btnAction" />
                                        </c:if>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty article}">
                    <h4>This article does not exist!!!</h4>
                </c:if>
                <script src="js/common.js"></script>
            </div>
        </div>
    </body>
</html>
