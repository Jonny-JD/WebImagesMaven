<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/style/personal.css">
    <link rel="stylesheet" href="../../resources/style/style.css">
    <link rel="stylesheet" href="../../resources/style/header/user-personal-header.css">

    <title>${sessionScope.user.name}</title>
</head>
<body id="personal">
<%@ include file="personal-header.jsp" %>

<main class="personal-images">
    <c:if test="${not empty requestScope.userImages}">

        <c:forEach var = "imagePath" items="${requestScope.userImages}">
            <article class="image-item">
                <img class="users-image" src="${pageContext.request.contextPath}/get-image/${imagePath}" alt="img">
            </article>
        </c:forEach>

    </c:if>
</main>
</body>
</html>
