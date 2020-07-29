<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/15
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>PictureInformation</title>
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/HeaderNavMainFooterPic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/PicInformation.css" rel="stylesheet">
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
                            <li><a href="${pageContext.request.contextPath}/myFriends"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/好友-未选中.png">MyFriends</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">LogOut</a></li>
                        </ul>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/jspFiles/login.jsp?last=${requestScope.image.imageId}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">LogIn</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>
<main>
    <div class="picture">
        <figure>
            <img src="${pageContext.request.contextPath}/resources/images/normal/medium/${requestScope.image.path}">
            <figcaption>
                <div class="PicDetails">
                    <h3>Title:</h3>
                    <p class="details">${requestScope.image.title}</p>
                </div>
                <div class="PicDetails">
                    <h3>Content:</h3>
                    <p class="details">${requestScope.image.content}</p>
                </div>
                <div class="PicDetails">
                    <h3>City:</h3>
                    <c:choose>
                        <c:when test="${!empty requestScope.image.city}">
                            <p class="details">${requestScope.image.city}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="details">(UNKNOWN)</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="PicDetails">
                    <h3>Country:</h3>
                    <p class="details">${requestScope.image.country}</p>
                </div>
                <div class="PicDetails">
                    <h3>Photographer:</h3>
                    <p class="details">${requestScope.image.author}</p>
                </div>
                <div class="PicDetails">
                    <h3>Update Time:</h3>
                    <p class="details">${requestScope.image.updateTime}</p>
                </div>
            </figcaption>
        </figure>
    </div>
    <div class="PicDescription">
        <h3>Description：</h3>
        <c:choose>
            <c:when test="${!empty requestScope.image.description}">
                <p>${requestScope.image.description}</p>
            </c:when>
            <c:otherwise>
                <p>The author is so lazy that he/she doesn't give any detailed description about this Photo.TUT</p>
            </c:otherwise>
        </c:choose>
    </div>
    <input type="hidden" value="${pageContext.request.contextPath}" id="info">
    <input type="hidden" value="${sessionScope.uid}" id="uid">
    <c:choose>
        <c:when test="${requestScope.likeOrNot eq \"yes\"}">
            <input type="button" id="collected" name="kudos" value="COLLECTED ${requestScope.image.kudoNum}">
        </c:when>
        <c:when test="${requestScope.likeOrNot eq \"no\"}">
            <input type="button" id="kudos" name="kudos" value="KUDOS ${requestScope.image.kudoNum}">
        </c:when>
        <c:otherwise>
            <p class="warning">You haven't logged in yet, click the button below to log in</p>
            <input type="button" id="noUidKudos" name="kudos" value="KUDOS ${requestScope.image.kudoNum}">
        </c:otherwise>
    </c:choose>
    <div id="commentsArea">
        <c:choose>
            <c:when test="${!empty requestScope.comments}">
                <c:choose>
                    <c:when test="${requestScope.rankMethod eq \"RankByTime\"}">
                        <img class="notRankByHot" alt="notRankByHot" src="${pageContext.request.contextPath}/resources/images/icons/热度.png">
                        <img class="rankByTime" alt="rankByTime" src="${pageContext.request.contextPath}/resources/images/icons/时间-on.png">
                    </c:when>
                    <c:otherwise>
                        <img class="rankByHot" alt="rankByHot" src="${pageContext.request.contextPath}/resources/images/icons/热度-on.png">
                        <img class="notRankByTime" alt="notRankByTime" src="${pageContext.request.contextPath}/resources/images/icons/时间.png">
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="comment">
                    <p class="PicComment">No comments yet ~ The SOFA is waiting for you!</p>
                </div>
            </c:otherwise>
        </c:choose>
        <c:forEach items="${requestScope.comments}" var="comment">
            <div class="comment">
                <h4 class="name">${comment.userName}</h4>
                <c:choose>
                    <c:when test="${empty sessionScope.uid}">
                        <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/赞.png">
                    </c:when>
                    <c:when test="${comment.statusOwn == 0}">
                        <img class="like" src="${pageContext.request.contextPath}/resources/images/icons/赞.png" alt="${comment.commentId}">
                    </c:when>
                    <c:when test="${comment.statusOwn == 1}">
                        <img class="likeYet" src="${pageContext.request.contextPath}/resources/images/icons/已赞.png" alt="${comment.commentId}">
                    </c:when>
                </c:choose>
                <p class="likeNum">${comment.kudos}</p>
                <c:if test="${sessionScope.uid eq comment.uid}">
                    <button class="deleteComment" value="${comment.commentId}">delete</button>
                </c:if>
                <p class="PicComment">${comment.concreteContent}</p>
            </div>
        </c:forEach>
        <input type="hidden" id="point">
        <c:if test="${!empty sessionScope.uid}">
            <form method="post" action="${pageContext.request.contextPath}/picInfo?method=comment">
                <textarea name="concreteContent" placeholder="Enter your comments here~ But remember to be polite!" required></textarea>
                <input type="hidden" value="${requestScope.image.imageId}" name="ImageID">
                <input type="submit" name="commentAdd" value="submit">
            </form>
        </c:if>
    </div>
</main>
<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/LikeOrNot.js"></script>
</body>
</html>
