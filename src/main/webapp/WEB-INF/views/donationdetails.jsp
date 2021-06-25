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
                <form action="<c:url value="/logout"/>" method="post">
                    <li><a href="/user/edit" class="btn btn--small btn--highlighted">Edycja moich danych</a></li>
                    <li><a href="/user/donations" class="btn btn--small btn--highlighted">Lista moich darów</a></li>
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
    <h2>Dar nr ${donation.id}</h2>
    <h3>Dla: ${donation.institution.name}</h3>
    <h3>Ilość worków: ${donation.quantity}</h3>
    <h3>W workach:</h3>
    <c:forEach items="${donation.categories}" var="category">
        <h3> - ${category.name}</h3>
    </c:forEach>
    <h3>Utworzono: ${donation.date}</h3>
    <h2>Szczegóły odbioru</h2>
    <h3>Ulica: ${donation.street}</h3>
    <h3>Miasto: ${donation.city}</h3>
    <h3>Kod pocztowy: ${donation.zipCode}</h3>
    <h3>Numer telefonu: ${donation.phone}</h3>
    <h3>Data: ${donation.pickUpDate}</h3>
    <h3>Godzina: ${donation.pickUpTime}</h3>
    <c:if test="${donation.pickupComment.length()>0}">
    <h3>Uwagi dla kuriera: ${donation.pickupComment}</h3>
    </c:if>
    <c:if test="${donation.status.status==1}"><h2>Odebrano ${donation.status.date}</h2></c:if>
    <c:if test="${donation.status.status==0}"><h2>Nie odebrano</h2></c:if>

</section>


<jsp:include page="footer.jsp"/>

<script src="<c:url value="/resources/js/app.js"/>"></script>
<script src="<c:url value="/resources/js/summary.js"/>"></script>
</body>
</html>