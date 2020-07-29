<%@ page import="com.travel.entity.Traveluser" %>
<%@ page import="java.util.List" %>
<%@ page import="com.travel.entity.Travelimage" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/11
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>收藏页</title>
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/HeaderNavMainFooterPic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/pageNavFooter.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/Favorites.css" rel="stylesheet">
</head>
<body onresize="squareClip()" onload="squareClip()">
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
                            <li><a <c:if test="${param.uid eq sessionScope.uid}">id="currentPage"</c:if> href="${pageContext.request.contextPath}/showFavor?uid=${sessionScope.uid}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/收藏-选中.png">MyFavorites</a></li>
                            <li><a href="${pageContext.request.contextPath}/myFriends"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/好友-未选中.png">MyFriends</a></li>
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
    <div class="showFavor">
        <ul id="FavoritesList">
            <h2 class="FavorTitle">${requestScope.favorOwner.userName}'s Favorite Photos</h2>
            <c:if test="${param.uid eq sessionScope.uid}">
                <c:if test="${requestScope.favorOwner.state == 1}"><img class="allowSee" src="${pageContext.request.contextPath}/resources/images/icons/view.png"></c:if>
                <c:if test="${requestScope.favorOwner.state != 1}"><img class="notAllowSee" src="${pageContext.request.contextPath}/resources/images/icons/view_off.png"></c:if>
            </c:if>
            <c:if test="${!empty requestScope.favorPics}">
                <c:forEach var="pic" items="${requestScope.favorPics}">
                    <li>
                        <div class="favoritePic">
                            <figure>
                                <div class="PicWrapper">
                                    <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${pic.imageId}"/>">
                                        <img class="normalPic" src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${pic.path}"/>">
                                    </a>
                                </div>
                                <figcaption>
                                    <div class="favorPicTopic">${pic.title}</div>
                                    <c:choose>
                                        <c:when test="${!empty pic.description}">
                                            <div class="description">${pic.description}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="description">The author is so lazy that he/she doesn't give any detailed description about this Photo.TUT</div>
                                        </c:otherwise>
                                    </c:choose>
                                </figcaption>
                                <c:if test="${param.uid eq sessionScope.uid}">
                                    <input class="removeFavor" type="button" name="Remove" value="remove" alt="<c:out value="${pic.imageId}"/>">
                                </c:if>
                            </figure>
                        </div>
                    </li>
                </c:forEach>
                <nav class="pageNumber">
                    <a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/到首页.png"></a>
                    <c:choose>
                        <c:when test="${param.page > 1}"><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page-1}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/上一页.png"></a></c:when>
                        <c:otherwise><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/上一页.png"></a></c:otherwise>
                    </c:choose>
                    <c:if test="${param.page > 2}"><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page-2}" class="pageButton">${param.page-2}</a></c:if>
                    <c:if test="${param.page > 1}"><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page-1}" class="pageButton">${param.page-1}</a></c:if>
                    <a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page}"><strong>${param.page}</strong></a>
                    <c:if test="${param.page < requestScope.pageSize}"><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page+1}">${param.page+1}</a></c:if>
                    <c:if test="${param.page + 1 < requestScope.pageSize}"><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page+2}">${param.page+2}</a></c:if>
                    <c:choose>
                        <c:when test="${requestScope.pageSize eq param.page}"><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/下一页.png"></a></c:when>
                        <c:otherwise><a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${param.page+1}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/下一页.png"></a></c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/showFavor?uid=${param.uid}&page=${requestScope.pageSize}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/到尾页.png"></a>
                </nav>
            </c:if>
        </ul>
        <!--如果没有收藏图片的提示，js中使用了一旦出现收藏图片就使其消失的方法-->
        <div class="NoFavorPic">
            <p>
                You haven't added any photo to your favorites, why not visit the browser?
            </p>
        </div>
    </div>
    <aside class="history">
        <h2>My Footprints</h2>
        <ul>
            <c:forEach items="${requestScope.historyPics}" var="hisPic">
                <li class="hisItem">
                    <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${hisPic.imageId}"/>">${hisPic.title}</a>
                </li>
            </c:forEach>
        </ul>
    </aside>
    <input type="hidden" id="info" value="${pageContext.request.contextPath}">
</main>
<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/ImgClip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/WhenNothing.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/RemoveLikePic.js"></script>
</body>
</html>
