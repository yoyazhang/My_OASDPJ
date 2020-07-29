<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyFriends</title>
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/HeaderNavMainFooterPic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/MyFriends.css" rel="stylesheet">
</head>
<body>
<header>
    <div class="title">
        <h1><a name="header">Travel To Share</a></h1>
        <div class = "slogan">Sponsored By Daddy Travel Agency</div>
    </div>
    <nav>
        <ul id="navPublic">
            <li><img id="logo" src="${pageContext.request.contextPath}/resources/images/icons/logo.png"></li>
            <li><a href="${pageContext.request.contextPath}/index.jsp"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/首页-未选中.png">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/jspFiles/Search.jsp"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/搜索-未选中.png">Search</a></li>
            <c:choose>
                <c:when test="${!empty sessionScope.uid}">
                    <li id="theDoor"><h2 id="myZone"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/个人中心.png">${sessionScope.username}</h2>
                        <ul id="navPersonal">
                            <li><a href="${pageContext.request.contextPath}/upload"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/上传-未选中.png">Upload</a></li>
                            <li><a href="${pageContext.request.contextPath}/myPhoto"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/我的照片-未选中.png">MyPhotos</a></li>
                            <li><a href="${pageContext.request.contextPath}/showFavor?uid=${sessionScope.uid}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/收藏-未选中.png">MyFavorites</a></li>
                            <li><a id="currentPage" href="${pageContext.request.contextPath}/myFriends"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/好友-选中.png">MyFriends</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">LogOut</a></li>
                        </ul>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/jspFiles/login.jsp"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">LogIn</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>
<main>
    <input type="hidden" id="application" value="${pageContext.request.contextPath}">
    <c:if test="${!empty requestScope.messageNOUser}">
        <script type="text/javascript">alert("No Username likes this, sorry")</script>
    </c:if>
    <aside>
        <ul>
            <h3>My Friends</h3>
            <c:forEach var="friend" items="${requestScope.friends}">
                <li>
                    <div class="user">
                        <c:if test="${friend.state != 1}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/view_off.png"></c:if>
                        <c:if test="${friend.state == 1}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/view.png"></c:if>
                        <h3 class="name">
                            <c:if test="${friend.state != 1}"><a href="" class="cantSee">${friend.userName}</a></c:if>
                            <c:if test="${friend.state == 1}"><a href="${pageContext.request.contextPath}/showFavor?uid=${friend.uid}">${friend.userName}</a></c:if>
                        </h3><!--href指向该用户的收藏页面-->
                        <div class="userInfo">
                            <p class="email">
                                <c:if test="${friend.state != 1}"><a href="" class="cantSee">${friend.email}</a></c:if>
                                <c:if test="${friend.state == 1}"><a href="${pageContext.request.contextPath}/showFavor?uid=${friend.uid}">${friend.email}</a></c:if>
                            </p><!--href指向该用户的收藏页面-->
                            <p class="registryDate">${friend.dateJoined}</p>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </aside>
    <div class="functionPart">
        <div class="SearchUser">
            <form role="form" action="${pageContext.request.contextPath}/myFriends" method="post">
                <input type="text" name="Username" placeholder="Enter the username you want to find" value="${param.Username}">
                <input type="submit" value="search" name="submit">
            </form>
            <c:if test="${!empty requestScope.searchUsers}">
                <section class="showSearchResults">
                    <c:forEach items="${requestScope.searchUsers}" var="searchUser">
                        <div class="searchFriend">
                            <h4 class="name">${searchUser.userName}</h4>
                            <c:choose>
                                <c:when test="${searchUser.status == \"yes\"}"><input type="button" name="send" value="send" class="send" alt="${searchUser.uid}"></c:when>
                                <c:otherwise><p class="status">${searchUser.status}</p></c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </section>
            </c:if>
        </div>
        <div class="requestISend">
            <h3>Request I Send:</h3>
            <c:forEach items="${requestScope.sendList}" var="sendUser">
                <div class="sendFriend">
                    <h4 class="name">${sendUser.userName}</h4>
                    <div class="status">Status: <p class="response">
                        <c:choose>
                            <c:when test="${sendUser.status == 1}">Accepted</c:when>
                            <c:when test="${sendUser.status == 2}">Rejected</c:when>
                            <c:otherwise>Unsolved</c:otherwise>
                        </c:choose>
                    </p></div>
                </div>
            </c:forEach>
        </div>
        <div class="requestIReceived">
            <h3>Request I Received: </h3>
            <c:forEach items="${requestScope.receiveList}" var="receiveUser">
                <div class="receivedFriend">
                    <h4 class="name">${receiveUser.userName}</h4>
                    <input type="button" value="reject" name="decision" class="reject" alt="${receiveUser.uid}">
                    <input type="button" value="accept" name="decision" class="accept" alt="${receiveUser.uid}">
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/ArrangeFriends.js"></script>
</body>
</html>
