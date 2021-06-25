<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--main-page">
    <nav class="container container--70">
        <ul class="nav--actions">

            <sec:authorize access="!isAuthenticated()">
                <li><a href="<c:url value="/login" /> " class="btn btn--small btn--without-border">Zaloguj</a></li>
                <li><a href="<c:url value="/register"/>"  class="btn btn--small btn--highlighted">Załóż konto</a></li>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <li><a href="<c:url value="/admin/institutions"/>" class="btn btn--small btn--highlighted">Fundacje</a></li>
                <li><a href="<c:url value="/admin/donations"/>" class="btn btn--small btn--highlighted">Dary</a></li>
                <li><a href="<c:url value="/admin/admins" />" class="btn btn--small btn--highlighted">Administratorzy</a></li>
                <li><a href="<c:url value="/admin/users" />" class="btn btn--small btn--highlighted">Użytkownicy</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <form action="<c:url value="/logout"/>" method="post">
                    <li><input class="btn btn--small btn--highlighted" type="submit" value="Wyloguj"></li>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>

            </sec:authorize>


        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>

</header>

<section class="help">
    <h2>Użytkownicy</h2>


    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>
            Panel zarządzania
        </p>

        <ul class="help--slides-items">

            <c:forEach items="${users}" var="user">
                <li>


                    <div class="col">
                        <div class="title">${user.username}</div>
                        <div class="subtitle">Id: ${user.id}</div>
                        <div class="subtitle">${user.name} ${user.surname}</div>
                    </div>


                    <c:if test="${user.enabled==1}">
                    <div class="col">
                        <div >
                            <a class="btn" href="<c:url value="/admin/disableuser/${user.id}"/>">ZABLOKUJ</a>
                        </div>
                    </div>
                    </c:if>

                    <c:if test="${user.enabled==0}">
                    <div class="col">
                        <div >
                            <a class="btn" href="<c:url value="/admin/enableuser/${user.id}"/>">ODBLOKUJ</a>
                        </div>
                    </div>
                    </c:if>

                    <div class="col">

                        <div >
                            <a class="btn" href="<c:url value="/admin/deleteuser/${user.id}"/>">USUŃ</a>
                        </div>
                    </div>


                </li>
            </c:forEach>
        </ul>

    </div>

</section>


<jsp:include page="footer.jsp"/>

<script src="<c:url value="/resources/js/app.js"/>"></script>
<script src="<c:url value="/resources/js/summary.js"/>"></script>
</body>
</html>