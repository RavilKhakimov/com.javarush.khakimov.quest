<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Если открыли index.jsp напрямую - перенаправляем на сервлет
    if (request.getAttribute("scene") == null) {
        response.sendRedirect("game");
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Лесной квест</title>
    <style>
        .inventory { border: 1px solid #ccc; padding: 10px; margin: 10px 0; }
        .message { color: green; font-weight: bold; margin: 10px 0; }
        .scene { margin: 20px 0; }
    </style>
</head>
<body>
<h1>Лесной квест</h1>

<!-- Показываем сообщение от действия -->
<c:if test="${not empty actionMessage}">
    <div class="message">${actionMessage}</div>
</c:if>

<!-- Показываем инвентарь -->
<c:if test="${not empty playerState.inventory}">
    <div class="inventory">
        <strong>Инвентарь:</strong>
        <c:forEach var="item" items="${playerState.inventory}">
            ${item.key} (${item.value})
        </c:forEach>
    </div>
</c:if>

<div class="scene">
    <!-- Пролог -->
    <c:if test="${scene.id == 0}">
        <pre>${scene.text}</pre>
        <form action="game" method="post">
            <label>Введите ваше имя: </label>
            <input type="text" name="playerName" required>
            <button type="submit">Начать игру</button>
        </form>
    </c:if>

    <!-- Основные сцены -->
    <c:if test="${scene.id != 0}">
        <h2>Сцена ${scene.id}</h2>

        <!-- Показываем флаги состояния -->
        <c:if test="${playerState.hasFlag('hasPitchfork')}">
            <p><em>У вас есть вилы</em></p>
        </c:if>

        <pre>${scene.text}</pre>

        <!-- Доступные действия -->
        <c:if test="${not empty scene.actions}">
            <form action="game" method="post">
                <c:forEach var="action" items="${scene.actions}">
                    <input type="radio" name="actionId" value="${action.actionId}"
                           id="action${action.actionId}">
                    <label for="action${action.actionId}">${action.text}</label><br>
                </c:forEach>
                <button type="submit">Выбрать</button>
            </form>
        </c:if>

        <!-- Финальные сцены -->
        <c:if test="${empty scene.actions}">
            <p><strong>Конец сцены!</strong></p>
            <p><a href="game?restart=true">Начать заново</a></p>
        </c:if>
    </c:if>
</div>
</body>
</html>