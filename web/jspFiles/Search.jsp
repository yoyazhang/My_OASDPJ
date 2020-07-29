<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/17
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>搜索页</title>
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/HeaderNavMainFooterPic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/pageNavFooter.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/Search.css" rel="stylesheet">
</head>
<body onload="squareClip()" onresize="squareClip()">
<header>
    <div class="title">
        <h1><a name="header">Travel To Share</a></h1>
        <div class = "slogan">Sponsored By Daddy Travel Agency</div>
    </div>
    <nav>
        <ul id="navPublic">
            <li><img id="logo" src="${pageContext.request.contextPath}/resources/images/icons/logo.png"></li>
            <li><a href="${pageContext.request.contextPath}/index.jsp"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/首页-未选中.png">Home</a></li>
            <li><a id="currentPage" href="${pageContext.request.contextPath}/jspFiles/Search.jsp"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/搜索-选中.png">Search</a></li>
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
                    <li><a href="${pageContext.request.contextPath}/jspFiles/login.jsp?last=search"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">LogIn</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>
<section id="SearchArea">
    <form action="${pageContext.request.contextPath}/search" method="get" role="form" id="SearchForm">
        <fieldset>
            <div class="writingArea">
                <label class="TitleSearch">
                    <p>Search: </p>
                    <c:choose>
                        <c:when test="${param.SearchMethod eq \"SearchByContent\" }">
                            <input type="radio" name="SearchMethod" value="SearchByTitle" id="TitleSearch"> Search By Title
                            <input type="radio" name="SearchMethod" value="SearchByContent" id="ContentSearch" checked> Search By Content
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="SearchMethod" value="SearchByTitle" id="TitleSearch" checked> Search By Title
                            <input type="radio" name="SearchMethod" value="SearchByContent" id="ContentSearch"> Search By Content
                        </c:otherwise>
                    </c:choose>
                    <input type="text" name="query" autofocus required value="${param.query}">
                </label>
            </div>
            <div class="rankMethod">
                <p>Choose the rank method:</p>
                <c:choose>
                    <c:when test="${param.RankMethod eq \"RankByTime\"}">
                        <label class="rankByHot">
                            <input type="radio" name="RankMethod" value="RankByHot"> Rank by heat rate
                        </label>
                        <label class="rankByTime">
                            <input type="radio" name="RankMethod" value="RankByTime" checked> Rank by Time
                        </label>
                    </c:when>
                    <c:otherwise>
                        <label class="rankByHot">
                            <input type="radio" name="RankMethod" value="RankByHot" checked> Rank by heat rate
                        </label>
                        <label class="rankByTime">
                            <input type="radio" name="RankMethod" value="RankByTime"> Rank by Time
                        </label>
                    </c:otherwise>
                </c:choose>

            </div>
            <input type="submit" name="SearchSubmit" value="SEARCH">
        </fieldset>
    </form>
</section>
<main>
    <ul id="SearchList">
        <c:forEach items="${requestScope.searchPics}" var="pic">
            <li>
                <div class="SearchPic">
                    <figure>
                        <div class="PicWrapper">
                            <a href="${pageContext.request.contextPath}/picInfo?id=<c:out value="${pic.imageId}"/>">
                                <img class="normalPic" src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${pic.path}"/>">
                            </a>
                        </div>
                        <figcaption>
                            <div class="SearchPicTopic">${pic.title}</div>
                            <c:choose>
                                <c:when test="${!empty pic.description}">
                                    <div class="description">${pic.description}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="description">The author is so lazy that he/she doesn't give any detailed description about this Photo.TUT</div>
                                </c:otherwise>
                            </c:choose>
                        </figcaption>
                    </figure>
                </div>
            </li>
        </c:forEach>
    </ul>
    <%
        String url = "/search?SearchMethod="+request.getParameter("SearchMethod")+"&query="+request.getParameter("query")+
        "&RankMethod="+request.getParameter("RankMethod");
    %>
    <nav class="pageNumber">
        <a href="${pageContext.request.contextPath}<%=url%>&page=1"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/到首页.png"></a>
        <c:choose>
            <c:when test="${param.page > 1}"><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page-1}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/上一页.png"></a></c:when>
            <c:otherwise><a href="${pageContext.request.contextPath}<%=url%>&page=1"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/上一页.png"></a></c:otherwise>
        </c:choose>
        <c:if test="${param.page > 2}"><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page-2}">${param.page-2}</a></c:if>
        <c:if test="${param.page > 1}"><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page-1}">${param.page-1}</a></c:if>
        <a href="${pageContext.request.contextPath}<%=url%>&page=${param.page}"><strong>${param.page}</strong></a>
        <c:if test="${param.page < requestScope.pageSize}"><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page+1}">${param.page+1}</a></c:if>
        <c:if test="${param.page + 1 < requestScope.pageSize}"><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page+2}">${param.page+2}</a></c:if>
        <c:choose>
            <c:when test="${requestScope.pageSize eq param.page}"><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/下一页.png"></a></c:when>
            <c:otherwise><a href="${pageContext.request.contextPath}<%=url%>&page=${param.page+1}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/下一页.png"></a></c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}<%=url%>&page=${requestScope.pageSize}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/到尾页.png"></a>
    </nav>
</main>
<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/ImgClip.js"></script>
</body>
</html>
