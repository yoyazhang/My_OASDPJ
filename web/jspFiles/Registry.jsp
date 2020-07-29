<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/10
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册界面</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/CSS/Registry.css" rel="stylesheet">
</head>
<body>
<header>
    <h1>TRAVEL TO SHARE</h1>
    <div class = "slogan">Sponsored By Daddy Travel Agency</div>
</header>
<main>
    <h2>We have been waiting for you long!</h2>
    <c:if test="${!empty requestScope.message}">
        <script>alert("This username has existed!");</script>
    </c:if>
    <c:if test="${!empty requestScope.messageVerifyCode}">
        <script type="text/javascript">alert("VerifyCode Wrong!");</script>
    </c:if>
    <input type="hidden" id="application" value="${pageContext.request.contextPath}">
    <form method="post" action="${pageContext.request.contextPath}/registry" role="form">
        <fieldset>
            <div class="user">
                <label>
                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/用户.png">USER:
                    <input type="text" name="username" value="${param.username}" id="name" required>
                </label>
            </div>
            <div class="email">
                <label>
                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/邮箱.png">EMAIL:
                    <input type="email" name="email" value="${param.email}" required>
                </label>
            </div>
            <div class="password">
                <label>
                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/icons/密码.png">PASSWORD:
                    <input type="password" id="password" name="password" value="${param.password}" required>
                    <div class="progress">
                        <div class="progress-bar" role="progressbar" aria-valuenow="60"
                             aria-valuemin="0" aria-valuemax="100">
                            <span class="sr-only"></span>
                        </div>
                    </div>
                </label><br>
            </div>
            <div class="password">
                <label>
                    PASSWORD DOUBLE-CHECK:
                    <input type="password" name="password2" value="${param.password}"  required>
                </label>
            </div>
            <div class="verifyCode">
                <label>
                    <img id="verifyCodePic" src="${pageContext.request.contextPath}/verifyCode">
                    <input type="text" id="Code" name="verifyCode" required placeholder="VerifyCode">
                </label>
            </div>
            <input type="hidden" name="salt">
            <input type="hidden" name="last" value="${param.last}">
            <input type="submit" name="registrySubmit" value="SUBMIT">
        </fieldset>
    </form>
</main>
<footer>
    <div class="information">YoYa Zhang版权所有 &copy 2019-2020</div>
    <div class="easterEgg">沪公网安备19302010074号</div>
    <address>Email: <a href = "http://mail.fudan.edu.cn/">Daddy&FudanSS@fudan.edu.cn</a></address>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JavaScript/verifyRegistry.js"></script>
</body>
</html>
