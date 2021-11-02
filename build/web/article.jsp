<%-- 
    Document   : article
    Created on : 26 Sep, 2021, 5:37:41 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="js/notification.js" type="text/javascript">
        <script type="text/javascript" src="js/common.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <title>Article Page</title>
    </head>
    <body onload="showNotification('${requestScope.NOTIFICATIONS}', 'notify')">
        <div class="wrapper">
            <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
            <c:if test="${result == null}">
                <%--<jsp:forward page="searchArticleAction"></jsp:forward>--%>
                <%--<c:redirect url = "searchArticleAction"/>--%>
            </c:if>
            <div>
                <form action="logoutAction">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                                <a href="checkLoginAction?btnAction=Post">Post Article</a>
                            </div>
                            <div class="welcome-content">
                                <input type="submit" value="Logout" name="btnAction" />
                            </div>
                        </c:if>
                        <c:if test="${empty wellcomeUser}">
                            <div>
                                <h3>Welcome, Quest.</h3>
                                <a href="checkLoginAction?btnAction=Post">Post Article</a>
                            </div>
                            <div class="welcome-content">
                                <a href="loginPageStatic">Login</a>
                                <a href="signUpPageStatic">Sign Up</a>
                            </div>
                        </c:if>
                    </div>
                </form>
            </div>
            <div class="container">
                <div style="border: solid black 1px;">
                    <c:set var="status" value="${requestScope.CURRENTSTATUS}"/>
                     <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                    <c:set var="pageIndex" value="${requestScope.CURRENTPAGEINDEX}"/>
                    <c:set var="pageSize" value="${requestScope.CURRENTPAGESIZE}"/>
                    <c:set var="searchContent" value="${param.txtSearchContent}"/>
                    <form action="searchArticleAction" id="searchForm">
                        <h2>Article Page</h2>
                        <label>Content: </label><input style="width: 200px;" type="text" name="txtSearchContent" value="${param.txtSearchContent}" placeholder="Input content"/>
                        <input type="hidden" name="txtSearchTittle" value="" />
                        <input type="hidden" name="cbStatusAr" value="2" />
                        <input type="hidden" name="txtPageSize" value="${pageSize}">
                        <input style="margin-top: 10px" type="submit" value="Search" name="btnAction" /><br/>
                    </form><br/>
                </div>
                <div style="border: solid black 1px; height: auto; display: flex; flex-direction: column;">
                    <c:if test="${not empty result}">
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                            <div class="article-container">
                                <div class="article-content">
                                    <c:url var="urlRewriting" value="detailAction">
                                        <c:param name="pk" value="${dto.id}"/>
                                        <c:param name="txtStatus" value="Active"/>
                                    </c:url>
                                    <a href="${urlRewriting}" class="item text_link" style="font-size: 20px; font-weight: 600;">${dto.tittle}</a>
                                    <span class="item">${dto.shortDescription}</span>
                                    <span class="item" style="font-size: 13px; font-style: italic">
                                        <div style="display: flex">
                                            <b style="margin-right: 4px">${dto.author}</b> - ${dto.time} - ${dto.createDate} - <img style="margin-left: 4px; margin-right: 4px" width="15" src="icon/chat.svg"/> ${dto.totalComment}
                                        </div>
                                    </span>
                                </div>
                            </div>
                        </c:forEach>
                        <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; margin-top: 20px">
                            <c:forEach var="index" begin="1" end="${totalPage}">
                                <a class="paging-container" 
                                   href="changePageAction?txtPageIndex=${index}&txtSearchTittle=&txtSearchContent=${searchContent}&cbStatusAr=${status}&txtPageSize=${pageSize}">
                                    <span style="color: grey; padding: 5px; padding-left: 15px; padding-right: 15px;" ${index == pageIndex? 'class="paging-content"': ''}>${index}</span>
                                </a>
                            </c:forEach>
                            <span style="margin-left: 5px; margin-right: 20px">-----</span>
                            <form action="changePageAction" id="pageSizeForm">
                                <select name="txtPageSize" style="font-size: 16px; padding: 5px;" 
                                        onchange="changePageSize(this, ${pageIndex}, '', '${searchContent}', ${status});">
                                    <option value="5" ${pageSize == 5? 'selected' : ''}>5</option>
                                    <option value="10" ${pageSize == 10? 'selected' : ''}>10</option>
                                    <option value="15" ${pageSize == 15? 'selected' : ''}>15</option>
                                    <option value="20" ${pageSize == 20? 'selected' : ''}>20</option>
                                </select>
                                <input type="hidden" name="txtSearchTittle" value="">
                                <input type="hidden" name="txtSearchContent" value="${searchContent}">
                                <input type="hidden" name="cbStatusAr" value="${status}">
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${empty result}">
                        <h3>No search results found!!!!</h3>
                    </c:if>
                    <div id="notify">
                        <h1 style="margin-top: 4%">${requestScope.NOTIFICATIONS}</h1>
                    </div>
                    <script src="js/notification.js"></script>
                </div> 
            </div>
        </div>
    </body>
</html>
