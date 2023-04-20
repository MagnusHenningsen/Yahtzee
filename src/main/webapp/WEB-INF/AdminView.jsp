<%@ page import="prosjektGruppe5.Entities.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="myAdminClass" class="prosjektGruppe5.Utilities.myAdminClass" scope="page" />

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <link rel="stylesheet" href="http://ider-database.westeurope.cloudapp.azure.com:8080/Prosjekt2Gruppe5/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<button id="GoHome" type="button" style="background-color: #fff; border: 1px solid #000; outline: none; position: relative; z-index: 1;" onclick="location.href='landing'">
    <i class="fa fa-home" aria-hidden="true"></i>
</button>


<div id="tableOfUsersForAdmin">
    <h2>Brukere</h2>
    <table>
        <thead>
        <tr>
            <th></th>
            <th>Brukernavn</th>
            <th>Fullt navn</th>
            <th>Epost</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${adminViewOfRegisteredUsers}">
            <c:if test="${not user.getUserName().equals(myAdminClass.adminUsername)}">
                <tr>
                    <td>
                        <form action="deleteUser" method="post">
                            <input type="hidden" name="userId" value="${user.getUserId()}"/>
                            <button type="submit" style="background-color: transparent; border: none; outline: none;">
                                <span style="color: red; font-size: 1.2em;">&times;</span>
                            </button>
                        </form>
                    </td>
                    <td>${user.getUserName()}</td>
                    <td>${user.getFullName()}</td>
                    <td>${user.getEmail()}</td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>
<br>

<div id="tableOfGames">
    <h2>Liste over Spill</h2>
    <table>
        <thead>
        <tr>
            <th></th>
            <th>Spill-ID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="game" items="${adminViewOfGames}">
        <tr>
            <td>
                <form action="deleteGame" method="post">
                    <input type="hidden" name="gameId" value="${game.getGameId()}"/>
                    <button type="submit" style="background-color: transparent; border: none; outline: none;">
                        <span style="color: red; font-size: 1.2em;">&times;</span>
                    </button>
                </form>
            </td>
            <td>${game.getGameId()}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>