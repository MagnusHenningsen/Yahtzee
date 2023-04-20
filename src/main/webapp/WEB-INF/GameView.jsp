<%@ page import="prosjektGruppe5.Entities.Round" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Yatzy</title>
    <link rel="stylesheet" href="/css/yahtzee.css"> <!-- Add CSS-fil -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<button id="backToHomePage" type="button" style="background-color: #fff; border: 1px solid #000; outline: none; position: relative; z-index: 1;" onclick="location.href='/landing'">
    <i class="fa fa-home" aria-hidden="true"></i>
</button>

<div id="GameContainer">
    <h1>Yatzy</h1>

    <div class="button-container">
        <div id="root">
            <button id="roll-dice-btn" class="game-button">Trill Terninger</button>
        </div>
        <form id="finishRoundForm">
            <button type="button" id="finishRound" class="game-button">Fullfør runde</button>
        </form>
    </div>



    <div id="dice-container">
    </div>

    <script src="/js/RollingTheDiceFunctions.js" defer></script>


    <table>
        <p style="font-size: 50px; text-shadow: 5px 5px red;" id="gameOverMsg"><b>${message}</b></p>
        <thead>
        <tr>
            <th class="highlighted-text">Deltagere:</th>
            <%-- Part of the code responsible for printing the name of the players in the first row --%>
            <c:forEach items="${playersInThisGame}" var="player">
                <th class="highlighted-text">${player.getPlayerPrimaryKey().getPerson().getUserName()}</th>
            </c:forEach>
        </tr>
        <%-- Part of the code responsible for printing the name of rounds in each row --%>
        <c:forEach items="${['Enere', 'Toere', 'Treere', 'Firere', 'Femere', 'Seksere', 'Sum', 'Bonus', 'Ett Par', 'To Par', 'Tre Like', 'Fire Like', 'Liten Straight', 'Stor Straight' , 'Hus', 'Sjangse', 'Yatzy', 'Totalt']}" var="roundName" varStatus="status" >
            <tr>
                <c:choose>
                    <c:when test="${roundName == 'Sum' || roundName == 'Bonus' || roundName == 'Totalt'}">
                        <th class="highlighted-text">${roundName}:</th>
                    </c:when>
                    <c:otherwise>
                        <th>${roundName}:</th>
                    </c:otherwise>
                </c:choose>

                <c:forEach items="${playersInThisGame}" var="player">
                    <c:choose>

                        <c:when test="${listOfRoundsPlayed == null}">
                            <th>-</th>
                        </c:when>

                        <c:when test="${listOfRoundsPlayed.size() > 0}">
                            <c:choose>

                                <c:when test="${listOfRoundsPlayed[0].person.userName == player.playerPrimaryKey.person.userName}">

                                    <th>
                                        <c:choose>

                                            <c:when test="${status.index == 6 || status.index == 17 || status.index == 7}">
                                                <%-- DO the calculation for sum (6) bonus (7) total (16)--%>
                                                <c:choose>
                                                    <c:when test="${status.index == 6}">
                                                        <c:choose>
                                                            <c:when test="${mySum == null}">
                                                                -
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${mySum}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>

                                                    <c:when test="${status.index == 17}">
                                                        <c:choose>
                                                            <c:when test="${myTotal == null}">
                                                                -
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${myTotal}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>

                                                    <c:when test="${status.index == 7}">
                                                        <c:choose>
                                                            <c:when test="${myBonus == null}">
                                                                -
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${myBonus}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>


                                                </c:choose>
                                            </c:when>

                                            <c:when test="${status.index <= 5}">
                                                <c:choose>
                                                    <c:when test="${status.index < listOfRoundsPlayed.size()}">
                                                        ${listOfRoundsPlayed.get(status.index).getPoints().toString()}
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>

                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${(status.index-2) < listOfRoundsPlayed.size()}">
                                                        ${listOfRoundsPlayed.get(status.index - 2).getPoints().toString()}
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </th>
                                </c:when>

                                <c:otherwise>
                                    <th>-</th>
                                </c:otherwise>

                            </c:choose>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </tr>
        </c:forEach>

        </thead>
    </table>

    <%
        Integer currentGame = (Integer) session.getAttribute("currentGame");
        out.println("<script>sessionStorage.setItem('currentGame', '" + currentGame + "');</script>");
    %>


</div>

</body>
</html>