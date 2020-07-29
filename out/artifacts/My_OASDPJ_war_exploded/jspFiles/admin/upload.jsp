<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/15
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传界面</title>
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/HeaderNavMainFooterPic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/upload.css" rel="stylesheet">
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
                            <li><a id="currentPage" href="${pageContext.request.contextPath}/upload"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/上传-选中.png">Upload</a></li>
                            <li><a href="${pageContext.request.contextPath}/myPhoto"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/我的照片-未选中.png">MyPhotos</a></li>
                            <li><a href="${pageContext.request.contextPath}/showFavor?uid=${sessionScope.uid}"><img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/收藏-未选中.png">MyFavorites</a></li>
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
    <input type="hidden" id="application" value="${pageContext.request.contextPath}">
    <c:choose>
        <c:when test="${!empty requestScope.image}"><form method="post" action="${pageContext.request.contextPath}/upload?method=modify" enctype="multipart/form-data"></c:when>
        <c:otherwise><form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data"></c:otherwise>
    </c:choose>
        <c:if test="${!empty requestScope.image}">
            <input type="hidden" name="ImageID" value="${requestScope.image.imageId}">
        </c:if>
        <fieldset>
            <c:choose>
                <c:when test="${!empty requestScope.image}">
                    <legend>Modify This Picture</legend>
                    <div class="uploadPic">
                        <img class="PicFromUser" src="${pageContext.request.contextPath}/resources/images/normal/medium/<c:out value="${ requestScope.image.path}"/>"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <legend>Upload New Picture</legend>
                    <div class="uploadPic">
                        <img class="PicFromUser" src=""/>
                        <p id="placeholder">No Picture Uploaded</p>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="wrap">
                <span>UPLOAD</span>
                <input type="file" name="photoUpload" id="file">
            </div>
            <label id="uploadPicTitle">Picture Title: <input type="text" name="UploadPhotoTitle" value="${requestScope.image.title}"></label>
            <label id="uploadDep">Description: <textarea name="UploadPhotoDescription">${requestScope.image.description}</textarea></label>
            <label id ="uploadPicContent">Content: <input type="text" name="content" value="${requestScope.image.content}"></label>
            <label id="uploadCountry">Country:
                <select name="Countries" onchange="setCity(this,this.form.Cities)" id="Countries" class="
                <c:choose>
                <c:when test="${!empty requestScope.image}">a${requestScope.image.cityCode}</c:when>
                <c:otherwise>up</c:otherwise>
                </c:choose>">
                    <c:forEach items="${requestScope.CouRegs}" var="cou">
                        <option value="${cou.iso}" <c:if test="${cou.iso eq requestScope.image.countryRegionCodeIso}">selected</c:if>>${cou.countryRegionName}</option>
                    </c:forEach>
                </select>
            </label>
            <label id="uploadCity">City:
                <select name="Cities" id="Cities">
                    <option value="default" selected>-Cities-</option>
                </select>
            </label>
            <c:choose>
                <c:when test="${!empty requestScope.image}">
                    <input type="button" value="MODIFY" class="modifyConfirm" name="UploadSubmit">
                </c:when>
                <c:otherwise>
                    <input type="button" value="SUBMIT" class="uploadConfirm" name="UploadSubmit">
                </c:otherwise>
            </c:choose>
        </fieldset>
    </form>
</main>
<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/upload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/TwoLevel.js"></script>
</body>
</html>
