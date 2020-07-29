<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/10
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href ="${pageContext.request.contextPath}/resources/CSS/LogIn.css" rel="stylesheet">
</head>
<body>
<header>
    <h1 class = "title">TRAVEL TO SHARE</h1>
    <div class = "slogan">Sponsored By Daddy Travel Agency</div>
</header>
<main>
    <input type="hidden" value="${pageContext.request.contextPath}" id="info">
    <c:if test="${!empty requestScope.message}">
        <script type="text/javascript">alert("UserName or password wrong!")</script>
    </c:if>
    <c:if test="${!empty requestScope.messageVerifyCode}">
        <script type="text/javascript">alert("VerifyCode Wrong!");</script>
    </c:if>
    <form action="${pageContext.request.contextPath}/login" method="post" role="form" id="loginForm">
        <fieldset>
            <legend>Welcome Back to TTS!</legend>
            <div class="user">
                <label><input type="text" name="usernameOrEmail" required placeholder="User Name/Email" value="${param.usernameOrEmail}"></label>
            </div>
            <div class="password">
                <label><input type="password" name="password" required placeholder="Password"></label>
            </div>
            <div class="verifyCode">
                <label><input type="text" name="verifyCode" required placeholder="VerifyCode"><img id="verifyCodePic" src="${pageContext.request.contextPath}/verifyCode"></label>
            </div>
            <input type="hidden" name="last" value="${param.last}">
            <input type="submit" name="login" value="LOG IN">
        </fieldset>
    </form>
    <div class="registerIntro">
        <a href="${pageContext.request.contextPath}/jspFiles/Registry.jsp?last=${param.last}">Join us right now if you haven't got a TTS account yet!</a>
    </div>
</main>

<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/login.js"></script>
</body>
</html>
