<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<div class="header">

    <div class="content">
        <a href="" class="circle">
            <img height="128" width="128"
                 src="../../resources/img/a-mountain-with-a-blue-sky-and-a-man-standing-on-the-edge-of-it.jpg"
                 alt="user-avatar">
        </a>
        <div class="user-panel">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button class="logout-button button" name="logout" type="submit"><fmt:message key="logout"/></button>
            </form>
        </div>
        <div>
            <form class="add-photo-block" action="${pageContext.request.contextPath}/add-image" method="post"
                  enctype="multipart/form-data">
                <label for="add-image" class="add_photo button"><fmt:message key="page.user.personal.select_file"/>
                    <input type="file" id="add-image" accept="image/png, image/jpeg" style="display:none" name="newImage" required/>
                </label>
                <button type="submit" class="add_photo button"><fmt:message
                        key="page.user.personal.add_photo"/></button>
            </form>
        </div>
    </div>
</div>

