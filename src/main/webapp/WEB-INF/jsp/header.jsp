<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="main-header">

    <div class="content">
        <div class="lang-button-block">

            <div id="locale">

                <form action="${pageContext.request.contextPath}/locale" method="post">
                    <button class="lang-button" name="lang" type="submit" value="ru_RU">ru</button>
                    <button class="lang-button" name="lang" type="submit" value="en_EN">en</button>
                </form>

            </div>
            <fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang :
                                                        (param.lang != null ? param.lang : 'en_EN')}"/>
            <fmt:setBundle basename="translations"/>
        </div>

        <c:if test="${not empty sessionScope.user}">

            <div class="logout-button-block">

                <div id="logout">

                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button class="logout-button lang-button" name="logout" type="submit"><fmt:message
                                key="logout"/></button>
                    </form>

                </div>

            </div>

        </c:if>
    </div>
</div>