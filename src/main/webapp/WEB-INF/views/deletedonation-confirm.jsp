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
                <li><a href="<c:url value="/register"/>" class="btn btn--small btn--highlighted">Załóż konto</a></li>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <li><a href="<c:url value="/admin/institutions"/>" class="btn btn--small btn--highlighted">Fundacje</a>
                <li><a href="<c:url value="/admin/donations"/>" class="btn btn--small btn--highlighted">Dary</a></li>
                </li>
                <li><a href="<c:url value="/admin/admins" />"
                       class="btn btn--small btn--highlighted">Administratorzy</a></li>
                <li><a href="<c:url value="/admin/users" />" class="btn btn--small btn--highlighted">Użytkownicy</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li><a href="/user/edit" class="btn btn--small btn--highlighted">Edycja moich danych</a></li>
                <li><a href="/user/donations" class="btn btn--small btn--highlighted">Lista moich darów</a></li>
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
    <h2>Potwierdź usunięcie daru nr ${donation.id}</h2>
    <h3>Dla: ${donation.institution.name}</h3>
    <h3>Ilość worków: ${donation.quantity}</h3>
    <h3>Utworzono: ${donation.date}</h3>


    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">

        <form action="/user/deletedonation-confirm" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${donation.id}"/>


            <button class="btn" type="submit">USUŃ</button>
            <button class="btn"><a href="<c:url value="/user/donations"/>">ANULUJ</a></button>
        </form>


    </div>

</section>


<jsp:include page="footer.jsp"/>

<script src="<c:url value="/resources/js/app.js"/>"></script>
<script src="<c:url value="/resources/js/summary.js"/>"></script>
</body>
</html>