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
<body id="registration">
<%@ include file="header.jsp" %>
<div class="registration-form">
    <h1>Register</h1>
    <form action="${pageContext.request.contextPath}/registration" method="post"
          enctype="application/x-www-form-urlencoded">
        <label for="name">
            <input type="text" name="name" placeholder="<fmt:message key="page.reg.name"/>" id="name" required/>
        </label>
        <label for="email">
            <input type="email" name="email" placeholder="<fmt:message key="page.login.email"/>" id="email" required/>
        </label>
        <label for="password">
            <input type="password" name="password" placeholder="<fmt:message key="page.login.password"/>" id="password"
                   required/>
        </label>
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="page.reg.send"/></button>
    </form>
    <p id="registration-login-ref"><fmt:message key="page.reg.login.message"/> <a
            href="${pageContext.request.contextPath}/login"><fmt:message key="page.login.link"/></a>
    </p>
    <div class="error-block">
        <c:if test="${not empty requestScope.errors}">
            <c:forEach var="error" items="${requestScope.errors}">
                <span class="error-message"><fmt:message key="${error.code}"/></span>
                <br>
            </c:forEach>
        </c:if>
    </div>
</div>

</body>
</html>