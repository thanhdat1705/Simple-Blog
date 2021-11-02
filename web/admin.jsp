<%-- 
    Document   : admin
    Created on : 27 Sep, 2021, 4:31:19 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/common.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <title>Admin Page</title>
    </head>
    <body>
        <div class="wrapper">
            <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
            <c:if test="${result == null}">
                <%--<jsp:forward page="searchArticleAction"></jsp:forward>--%>
                <c:redirect url = "searchArticleAction"/>
            </c:if>
            <c:set var="listStatus" value="${requestScope.ARTICLE_STATUS_LIST}"/>
            <div>
                <form action="logoutAction">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <c:set var="test" value="${sessionScope.TEST}"/>
                    <c:if test="${empty wellcomeUser}">
                        <%--<jsp:forward page="logoutAction"></jsp:forward>--%>
                        <c:redirect url = "logoutAction"/>
                    </c:if>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                            </div>
                            <div class="welcome-content">
                                <input type="submit" value="Logout" name="btnAction" />
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
                    <c:set var="searchTittle" value="${param.txtSearchTittle}"/>
                    <c:set var="searchContent" value="${param.txtSearchContent}"/>
                    <form action="searchArticleAction" id="searchForm">
                        <h2>Article Page</h2>
                        <label>Tittle: </label><input style="width: 200px; margin-right: 10px" type="text" name="txtSearchTittle" value="${param.txtSearchTittle}" placeholder="Input tittle"/>
                        <label>Content: </label><input style="width: 200px;" type="text" name="txtSearchContent" value="${param.txtSearchContent}" placeholder="Input content"/>
                        <select name="cbStatusAr">
                            <c:if test="${not empty listStatus}">
                                <c:forEach var="item" items="${listStatus}" varStatus="counter">
                                    <option value="${item.id}" ${status == item.id? 'selected' : ''}>${item.name}</option>
                                </c:forEach>
                            </c:if>
                        </select><br/>
                        <input type="hidden" name="txtPageSize" value="${pageSize}">
                        <input style="margin-top: 10px" type="submit" value="Search" name="btnAction" /><br/>
                    </form><br/>
                </div>
                <div style="border: solid black 1px; height: auto;">

                    <c:if test="${not empty result}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Tittle</th>
                                    <th>Short Description</th>
                                    <th>Content</th>
                                    <th>Email</th>
                                    <th>Author</th>
                                    <th>Date</th>
                                    <th>Status</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dto" items="${result}" varStatus="counter">
                                <form action="updateArticleStatusAction" method="POST">
                                    <tr>
                                        <td style="text-align: center">${(counter.count - 1) + (pageIndex * pageSize) - (pageSize-1)}</td>
                                        <td> 
                                            <c:url var="urlRewriting" value="detailAction">
                                                <c:param name="pk" value="${dto.id}"/>
                                                <c:param name="txtStatus" value="${dto.status}"/>
                                            </c:url>
                                            <a href="${urlRewriting}" style="text-decoration: none; color: blue" >${dto.tittle}</a>
                                            <input type="hidden" name="pk" value="${dto.id}" />
                                        </td>
                                        <td>${dto.shortDescription} </td>
                                        <td>${dto.content} </td>
                                        <td style="text-align: center">${dto.email} </td>   
                                        <td style="width: 90px; text-align: center"> ${dto.author} </td>
                                        <td style="text-align: center">${dto.time} - ${dto.createDate} </td>
                                        <td style="text-align: center">${dto.status} </td>
                                        <c:if test="${dto.status == 'New'}">
                                            <td style="text-align: center">
                                                <input style="width: 80px" type="submit" value="Approval" name="btnAction" />
                                                <input style="width: 80px; margin-top: 5px" type="submit" value="Delete" name="btnAction" />
                                            </td>
                                        </c:if>
                                        <c:if test="${dto.status == 'Active'}">
                                            <td style="text-align: center">
                                                <input style="width: 80px" type="submit" value="Delete" name="btnAction" />
                                            </td>
                                        </c:if>
                                        <c:if test="${dto.status == 'Deleted'}">
                                            <td style="text-align: center">
                                                <input style="width: 80px" type="submit" value="Restore" name="btnAction" />
                                            </td>
                                        </c:if>
                                    </tr> 
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; margin-top: 20px">
                            <c:forEach var="index" begin="1" end="${totalPage}">
                                <a class="paging-container" 
                                   href="changePageAction?txtPageIndex=${index}&txtSearchTittle=${searchTittle}&txtSearchContent=${searchContent}&cbStatusAr=${status}&txtPageSize=${pageSize}">
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
                                <input type="hidden" name="txtSearchTittle" value="${searchTittle}">
                                <input type="hidden" name="txtSearchContent" value="${searchContent}">
                                <input type="hidden" name="cbStatusAr" value="${status}">
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${empty result}">
                        <h3>No search results found!!!!</h3>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
