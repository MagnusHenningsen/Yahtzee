<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="myAdminClass" class="prosjektGruppe5.Utilities.myAdminClass" scope="page" />

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="http://ider-database.westeurope.cloudapp.azure.com:8080/Prosjekt2Gruppe5/css/login.css">
</head>
<body>
<form action="login" method="post">
    <fieldset><legend>Logg Inn</legend>

        <label for="username">Brukernavn: </label> <input type="text" id="username" name="username"/><br>
        <label for="password">Passord:</label> <input type="password" id="password" name="password"><br>
        <input type="hidden" name="adminInfo" id="adminInfo" value="${myAdminClass.adminUsername}">
        <input type="submit" value="Logg in"/><br><a href="register">Lag ny bruker</a><br>
        <p style="color:red">${error}</p>
    </fieldset>
</form>
</body>
</html>