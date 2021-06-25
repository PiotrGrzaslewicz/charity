<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">
    <jsp:include page="header.jsp"/>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br />
                <span class="uppercase">potrzebującym</span>
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                <ul class="slogan--steps-boxes">
                    <li>
                        <div><em>1</em><span>Wybierz rzeczy</span></div>
                    </li>
                    <li>
                        <div><em>2</em><span>Spakuj je w worki</span></div>
                    </li>
                    <li>
                        <div><em>3</em><span>Wybierz fundację</span></div>
                    </li>
                    <li>
                        <div><em>4</em><span>Zamów kuriera</span></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>

<section class="form--steps">
    <div class="form--steps-instructions">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1" class="active">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3">
                Wybierz jedną, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4">Podaj adres oraz termin odbioru rzeczy.</p>
        </div>
    </div>

    <div class="form--steps-container">
        <div class="form--steps-counter">Krok <span>1</span>/4</div>

        <form:form action="/donation" method="post" modelAttribute="donation">
            <!-- STEP 1: class .active is switching steps -->
            
            <div id="categories" data-step="1" class="active">
                <h3>Zaznacz co chcesz oddać:</h3>
                <label style="color: red">${categoryMessage}</label>

               <c:forEach items="${allCategories}" var="category">

                   <div  class="form-group form-group--checkbox">
                       <label>
                           <input
                                   type="checkbox"
                                   name="categories"
                                   value="${category.id}"
                           />
                           <span class="checkbox"></span>
                           <span class="description">${category.name}</span>
                       </label>
                   </div>


               </c:forEach>


                <div class="form-group form-group--buttons">
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 2 -->
            <div data-step="2">
                <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>
                <label style="color: red"><form:errors path="quantity"/> </label>
                <div class="form-group form-group--inline">
                    <label>
                        Liczba 60l worków:
                        <form:input id="quantity" type="number" name="bags" step="1" min="1"  path="quantity" value="1"/>
                    </label>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>



            <!-- STEP 4 -->
            <div id="institutions" data-step="3">
                <h3>Wybierz organizację, której chcesz pomóc:</h3>
                <label style="color: red"><form:errors path="institution"/></label>

                <c:forEach var="institution" items="${institutions}">
                <div class="form-group form-group--checkbox">
                    <label>
                        <form:radiobutton name="institution" value="${institution.id}"  path="institution"/>
                        <span class="checkbox radio"></span>
                        <span class="description">
                  <div class="title">${institution.name}</div>
                  <div class="subtitle">
                    ${institution.description}
                  </div>
                </span>
                    </label>
                </div>
                </c:forEach>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 5 -->
            <div data-step="4">
                <h3>Podaj adres oraz termin odbioru rzecz przez kuriera:</h3>

                <div class="form-section form-section--columns">
                    <div class="form-section--column">
                        <h4>Adres odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label style="color: red"><form:errors path="street"/></label>
                            <label> Ulica <form:input id="street" type="text" name="address"  path="street"/> </label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label style="color: red"><form:errors path="city"/></label>
                            <label> Miasto <form:input id="city" type="text" name="city" path="city" /> </label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label style="color: red"><form:errors path="zipCode"/></label>
                            <label>
                                Kod pocztowy <form:input id="zipCode" type="text" name="postcode" path="zipCode"/>
                            </label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label style="color: red"><form:errors path="phone"/></label>
                            <label>
                                Numer telefonu <form:input id="phone" type="phone" name="phone"  path="phone"/>
                            </label>
                        </div>
                    </div>

                    <div class="form-section--column">
                        <h4>Termin odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label style="color: red"><form:errors path="pickUpDate"/></label>
                            <label> Data <form:input id="date" type="date" name="data"  path="pickUpDate"/> </label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label style="color: red"><form:errors path="pickUpTime"/></label>
                            <label> Godzina <form:input id="time" type="time" name="time"  path="pickUpTime"/> </label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Uwagi dla kuriera
                                <form:textarea id="details" name="more_info" rows="5" path="pickupComment"></form:textarea>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 6 -->
            <div data-step="5">
                <h3>Podsumowanie Twojej darowizny</h3>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li >
                                <span class="icon icon-bag"></span>
                                <span id="quantityCategory" class="summary--text">4 worki ubrań w dobrym stanie dla dzieci</span>

                            </li>

                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text" id="summaryInstitution"
                                >Dla fundacji "Mam marzenie" w Warszawie</span
                                >
                            </li>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li id="summaryStreet">Prosta 51</li>
                                <li id="summaryCity">Warszawa</li>
                                <li id="summaryZipCode">99-098</li>
                                <li id="summaryPhone">123 456 789</li>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li id="summaryDate">13/12/2018</li>
                                <li id="summaryTime"> 15:40</li>
                                <li id="summaryComments">Brak uwag</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="submit" class="btn">Potwierdzam</button>
                </div>
            </div>
        </form:form>
    </div>
</section>

<jsp:include page="footer.jsp"/>

<script src="<c:url value="/resources/js/app.js"/>"></script>
<script src="<c:url value="/resources/js/summary.js"/>"></script>
</body>
</html>