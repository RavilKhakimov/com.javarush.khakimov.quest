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
    <title>Будь как дома, путник.</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/webjars/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .game-container { max-width: 800px; margin: 0 auto; }
        .scene-text {
            white-space: pre-wrap;
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 6px;
            border-left: 3px solid #007bff;
            line-height: 1.5;
            margin-bottom: 15px;
        }
        .actions-list { margin: 10px 0; }
        .form-check.action-choice {
            margin-bottom: 5px;
            padding: 8px 10px;
            border-radius: 4px;
            border: 1px solid #dee2e6;
        }
        .form-check.action-choice:hover { background-color: #f1f3f4; }
    </style>
</head>
<body class="bg-light">
<div class="container my-4 game-container">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="h3 mb-0">Будь как дома, путник.</h1>
                <c:if test="${scene.id != 0}">
                    <span class="badge bg-light text-dark">Игра №: ${playerState.gamesPlayed}</span>
                </c:if>
            </div>
        </div>

        <div class="card-body">
            <!-- Сообщение от действия -->
            <c:if test="${not empty actionMessage}">
                <div class="alert alert-success alert-dismissible fade show">
                        ${actionMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Инвентарь -->
            <c:if test="${not empty playerState.inventory}">
                <div class="alert alert-info">
                    <strong>Инвентарь:</strong>
                    <c:forEach var="item" items="${playerState.inventory}">
                        <span class="badge bg-secondary me-1">${item.key} (${item.value})</span>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Флаги состояния -->
            <c:if test="${playerState.hasFlag('hasPitchfork')}">
                <div class="alert alert-warning py-2">
                    <small><strong>У вас есть вилы</strong></small>
                </div>
            </c:if>

            <!-- Содержание сцены -->
            <div class="scene-text">${scene.text}</div>

            <!-- Форма для разных типов сцен -->
            <c:choose>
                <%-- Пролог (сцена 0) -- форма ввода имени --%>
                <c:when test="${scene.id == 0}">
                    <form action="game" method="post" class="mt-4">
                        <div class="mb-3">
                            <label for="playerName" class="form-label">Введите ваше имя:</label>
                            <input type="text" class="form-control" id="playerName" name="playerName" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-lg">Начать игру</button>
                    </form>
                </c:when>

                <%-- Сцены с действиями --%>
                <c:when test="${not empty scene.actions}">
                    <form action="game" method="post">
                        <h5 class="mb-2">Выберите действие:</h5>
                        <div class="actions-list">
                            <c:forEach var="action" items="${scene.actions}">
                                <div class="form-check action-choice">
                                    <input class="form-check-input" type="radio"
                                           name="actionId" value="${action.actionId}"
                                           id="action${action.actionId}" required>
                                    <label class="form-check-label" for="action${action.actionId}">
                                            ${action.text}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                        <button type="submit" class="btn btn-success w-100 mt-3">Выбрать</button>
                    </form>
                </c:when>

                <%-- Финальные сцены (без действий) --%>
                <c:otherwise>
                    <div class="text-center">
                        <div class="alert alert-info">
                            <h4>Конец сцены!</h4>
                        </div>
                        <form action="game" method="post">
                            <input type="hidden" name="restart" value="true">
                            <button type="submit" class="btn btn-outline-primary">Начать заново</button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="card-footer text-muted text-center">
            <small>Как то так....</small>
        </div>
    </div>
</div>

<script src="/webjars/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const playerNameField = document.getElementById('playerName');
        if (playerNameField) {
            playerNameField.focus();
        }
    });
</script>
</body>
</html>