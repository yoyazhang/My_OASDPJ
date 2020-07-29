<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/10
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>TravelToShare首页</title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

  <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/CSS/HeaderNavMainFooterPic.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/CSS/Home.css" rel="stylesheet">

</head>
<body onload="squareClip()" onresize="squareClip()">
<header>
  <c:if test="${empty requestScope.hotPics}">
    <c:redirect url="/showIndex"/>
  </c:if>
  <div class="title">
    <h1><a name="header">Travel To Share</a></h1>
    <div class = "slogan">Sponsored By Daddy Travel Agency</div>
  </div>
  <nav>
    <ul id="navPublic">
      <li><img id="logo" src="${pageContext.request.contextPath}/resources/images/icons/logo.png"></li>
      <li><a id="currentPage" href="${pageContext.request.contextPath}/index.jsp"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/首页-选中.png">Home</a></li>
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
          <li><a href="${pageContext.request.contextPath}/jspFiles/login.jsp?last=index"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">LogIn</a></li>
        </c:otherwise>
      </c:choose>
    </ul>
  </nav>
</header>
<main>
  <div class="container">
    <div id="myCarousel" class="carousel slide">
      <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
          <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
          <li data-target="#myCarousel" data-slide-to="1"></li>
          <li data-target="#myCarousel" data-slide-to="2"></li>
          <li data-target="#myCarousel" data-slide-to="3"></li>
          <li data-target="#myCarousel" data-slide-to="4"></li>
      </ol>
      <!-- 图片 -->
      <div class="carousel-inner">
        <div class="item active">
          <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${requestScope.hotPics[0].imageId}"/>">
            <img src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${requestScope.hotPics[0].path}"/>">
          </a>
          <div class="carousel-caption">
            <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${requestScope.hotPics[0].imageId}"/>">
              ${requestScope.hotPics[0].title}
            </a>
          </div>
        </div>
        <c:forEach items="${requestScope.hotPics}" var="pic" begin="1">
          <div class="item">
            <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${pic.imageId}"/>">
              <img src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${pic.path}"/>">
            </a>
            <div class="carousel-caption">
              <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${pic.imageId}"/>">${pic.title}</a>
            </div>
          </div>
        </c:forEach>
      </div>
      <!-- 轮播（Carousel）导航 -->
      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
  </div>

  <section class="homePics">
    <table>
      <tr>
        <c:forEach items="${requestScope.newestPics}" var="pic" begin="0" end="2">
          <td>
            <figure>
              <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${pic.imageId}"/>">
                <img  class="normalPic" src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${pic.path}"/>">
              </a>
              <figcaption>
                <h3>${pic.title}</h3>
                <div class="details">
                    ${pic.author}
                  <br>
                  <p>${pic.updateTime}</p>
                </div>
              </figcaption>
            </figure>
          </td>
        </c:forEach>
      </tr>
      <tr>
        <c:forEach items="${requestScope.newestPics}" var="pic" begin="3" end="5">
          <td>
            <figure>
              <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${pic.imageId}"/>">
                <img  class="normalPic" src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${pic.path}"/>">
              </a>
              <figcaption>
                <h3>${pic.title}</h3>
                <div class="details">
                    ${pic.author}
                  <br>
                  <p>${pic.updateTime}</p>
                </div>
              </figcaption>
            </figure>
          </td>
        </c:forEach>
      </tr>
    </table>
  </section>
  <aside>
    <button id="toTop"><a href="#header"><img src="${pageContext.request.contextPath}/resources/images/icons/向上.png"></a></button>
  </aside>
</main>
<footer>
  <div class="contact">
    <div><a href="#">About us</a></div>
    <div><a href="#">Contact us</a></div>
  </div>
  <div class="Information">
    <div class="introduction">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
  </div>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/ImgClip.js"></script>
</body>
</html>
