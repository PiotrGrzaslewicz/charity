<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<nav class="container container--70">
  <ul class="nav--actions">

    <sec:authorize access="!isAuthenticated()">
      <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
      <li><a href="/register" class="btn btn--small btn--highlighted">Załóż konto</a></li>
    </sec:authorize>

    <sec:authorize access="hasRole('ADMIN')">
      <li><a href="/admin/institutions" class="btn btn--small btn--highlighted">Fundacje</a></li>
      <li><a href="<c:url value="/admin/donations"/>" class="btn btn--small btn--highlighted">Dary</a></li>
      <li><a href="/admin/admins" class="btn btn--small btn--highlighted">Administratorzy</a></li>
      <li><a href="/admin/users" class="btn btn--small btn--highlighted">Użytkownicy</a></li>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">


      <li><a href="/user/edit" class="btn btn--small btn--highlighted">Edycja moich danych</a></li>
      <li><a href="/user/donations" class="btn btn--small btn--highlighted">Lista moich darów</a></li>
      <li>
      <form action="<c:url value="/logout"/>" method="post">
       <input class="btn btn--small btn--highlighted" type="submit" value="Wyloguj">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      </form></li>

    </sec:authorize>


  </ul>

  <ul>
    <li><a href="/" class="btn btn--without-border active">Start</a></li>
    <li><a href="#info" class="btn btn--without-border">O co chodzi?</a></li>
    <li><a href="#about" class="btn btn--without-border">O nas</a></li>
    <li><a href="#institutions" class="btn btn--without-border">Fundacje i organizacje</a></li>
    <li><a href="#contact" class="btn btn--without-border">Kontakt</a></li>
  </ul>
</nav>