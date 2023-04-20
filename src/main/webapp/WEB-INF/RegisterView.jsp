<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="http://ider-database.westeurope.cloudapp.azure.com:8080/Prosjekt2Gruppe5/css/login.css">
</head>
<body>

<form action="register" method="post">
    <fieldset><legend>Registrer</legend>

        <label for="username">Brukernavn: </label> <input type="text" id="username" name="username"/><br>
        <label for="password">Passord:</label> <input type="password" id="password" name="password"><br>
        <label for="fullName">Fullt Navn:</label> <input type="text" id="fullName" name="fullName"><br>
        <label for="email">Epost:</label> <input type="email" id="email" name="email"><br>
        <input type="submit" value="Registrer"/><br>Har du allerede bruker? <a href="/login">Logg inn her</a>
        <p style="color:red">${error}</p>
        <p style="color: red">${registerError}</p>
    </fieldset>
</form>
</body>
</html>