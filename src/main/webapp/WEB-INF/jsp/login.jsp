<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>

    <link rel="stylesheet" href="../../resources/style/style.css">
    <link rel="stylesheet" href="../../resources/style/entry.css">
    <link rel="stylesheet" href="../../resources/style/header/header.css">
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body id="login">
<%@ include file="header.jsp" %>
<div class="login-form">
    <h1>Login</h1>
    <form action="${pageContext.request.contextPath}/login" method="post" enctype="application/x-www-form-urlencoded">
        <label for="email">
            <input type="email" name="email" placeholder="<fmt:message key="page.login.email"/>" id="email" required/>
        </label>
        <label for="password">
            <input type="password" name="password" placeholder="<fmt:message key="page.login.password"/>" id="password"
                   required/>
        </label>
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="page.login.login"/></button>
    </form>
    <p id="registration-login-ref"><fmt:message key="page.login.register.message"/> <a
            href="${pageContext.request.contextPath}/registration"><fmt:message key="page.reg.link"/> </a>
    </p>
    <c:if test="${param.error != null}">
        <div class="error-block">
            <c:if test="${param.email != null}">
            <span class="error-message"><fmt:message key="page.login.error.incorrect-data" /></span>
            </c:if>
            <c:if test="${param.log_in.equals('false')}">
                <span class="error-message"><fmt:message key="page.login.error.not_logged_in" /></span>
            </c:if>
        </div>
    </c:if>
</div>


</body>
</html>