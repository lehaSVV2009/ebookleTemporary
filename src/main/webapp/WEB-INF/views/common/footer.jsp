<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<footer>

    <spring:message code="footer_text" var="footerText"/>

    <div class="container">

        Language : <a href="?language=en">English</a>|<a href="?language=ru_RU">Chinese</a>

        <h3>
            welcome.springmvc : <spring:message code="application_name" text="default text" />
        </h3>

        Current Locale : ${pageContext.response.locale}

        <span>
            <a href="?lang=de"><img src="http://localhost:8080/web-resources/img/rus16.png"/>rus</a>
            <a href="?lang=en"><img src="http://localhost:8080/web-resources/img/eng16.png"/>eng</a>
        </span>

        ${footerText}

        <span style="float: right">
            <a href="?theme=dark">dark</a> |
            <a href="?theme=light">light</a>
        </span>

    </div>
</footer>
