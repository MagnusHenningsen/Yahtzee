<%@ page import="prosjektGruppe5.Entities.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="myAdminClass" class="prosjektGruppe5.Utilities.myAdminClass" scope="page" />


<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <link rel="stylesheet" href="http://ider-database.westeurope.cloudapp.azure.com:8080/Prosjekt2Gruppe5/css/landing.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<button id="logOut" type="button" style="background-color: #fff; border: 1px solid #000; outline: none; position: relative; z-index: 1;" onclick="location.href='/'">
    <i class="fa fa-unlock" aria-hidden="true"></i>
</button>

<!--//Remember test123 is the admin -->
<div id="menuContainer">
    <% if (request.getSession().getAttribute("username").equals(myAdminClass.getAdminUsername())) { %>
    <button id="adminButton" onclick="location.href='admin'">Admin-Knapp</button>
    <% } %>

    <form style="margin:20px" action="landing" method="post"  id="getNewGameForm">
        <input type="submit" value="Start nytt spill" id="startNewGameButton">
        <input type="hidden" name="adminId" id="adminId" value="${myAdminClass.adminUsername}"/>
    </form>


    <% if (request.getSession().getAttribute("game") != null) { %>
    <div class="games" id="individualGames">
        <c:forEach items="${game}" var="item">
            <div class="game">
                <form:form method="post" action="landing/game">
                    <input type="text" id="gameId" name="gameId" readonly="true" value="${item.gameId}">
                    <input type="text" id="gameStatus" name="gameStatus" readonly="true" value="${item.gameStatus}">
                    <input type="submit" value="Bli med">
                </form:form>
                <br>
            </div>
        </c:forEach>
    </div>
    <% } %>

</div>



</body>
</html>