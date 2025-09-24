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

    <!-- Локальный Bootstrap CSS -->
    <link href="/webjars/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .game-container {
            max-width: 800px;
            margin: 0 auto;
        }
        .scene-text {
            white-space: pre-wrap;
            font-family: inherit;
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 6px;
            border-left: 3px solid #007bff;
            line-height: 1.5;
            margin-bottom: 15px;
            font-size: 0.95em;
        }

        /* Убираем старые стили и делаем компактнее */
        .actions-list {
            margin: 10px 0;
        }

        /* Упрощаем стили для выбора действий */
        .form-check.action-choice {
            margin-bottom: 5px !important;
            padding: 8px 10px !important;
            border-radius: 4px;
            border: 1px solid #dee2e6;
            transition: all 0.2s ease;
        }

        .form-check.action-choice:hover {
            background-color: #f1f3f4;
            border-color: #adb5bd;
        }

        .form-check-input {
            margin-top: 0.3em;
        }

        .form-check-label {
            cursor: pointer;
            font-size: 0.95em;
            margin-left: 5px;
        }

        /* Делаем кнопку компактнее */
        .btn-submit {
            margin-top: 15px;
            padding: 10px;
            font-size: 1em;
        }

        /* Уменьшаем отступы карточки */
        .card-body {
            padding: 1.25rem;
        }

        /* Компактный заголовок сцены */
        .scene-header {
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #dee2e6;
        }
    </style>
</head>
<body class="bg-light">
<div class="container my-4 game-container">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h1 class="h3 mb-0">Будь как дома, путник.</h1>
        </div>

        <div class="card-body">
            <!-- Показываем сообщение от действия -->
            <c:if test="${not empty actionMessage}">
                <div class="alert alert-success alert-dismissible fade show">
                        ${actionMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Показываем инвентарь -->
            <c:if test="${not empty playerState.inventory}">
                <div class="alert alert-info">
                    <strong>Инвентарь:</strong>
                    <c:forEach var="item" items="${playerState.inventory}">
                        <span class="badge bg-secondary me-1">${item.key} (${item.value})</span>
                    </c:forEach>
                </div>
            </c:if>

            <div class="scene">
                <!-- Пролог -->
                <c:if test="${scene.id == 0}">
                    <div class="scene-text mb-4">${scene.text}</div>
                    <form action="game" method="post" class="mt-4">
                        <div class="mb-3">
                            <label for="playerName" class="form-label">Введите ваше имя:</label>
                            <input type="text" class="form-control" id="playerName" name="playerName" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-lg">Начать игру</button>
                    </form>
                </c:if>

                <!-- Основные сцены -->
                <c:if test="${scene.id != 0}">
                    <!-- Компактный заголовок -->
                    <div class="scene-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="text-muted small">Текущая локация</span>
                            <span class="badge bg-success small">${playerState.name}</span>
                        </div>
                    </div>

                    <!-- Флаги состояния -->
                    <c:if test="${playerState.hasFlag('hasPitchfork')}">
                        <div class="alert alert-warning py-2">
                            <small><strong>У вас есть вилы</strong></small>
                        </div>
                    </c:if>

                    <div class="scene-text">${scene.text}</div>

                    <!-- Доступные действия -->
                    <c:if test="${not empty scene.actions}">
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
                            <button type="submit" class="btn btn-success btn-submit w-100">
                                Выбрать
                            </button>
                        </form>
                    </c:if>

                    <!-- Финальные сцены -->
                    <c:if test="${empty scene.actions}">
                        <div class="text-center">
                            <div class="alert alert-info">
                                <h4>Конец сцены!</h4>
                            </div>
                            <a href="game?restart=true" class="btn btn-outline-primary">Начать заново</a>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </div>

        <div class="card-footer text-muted text-center">
            <small>Как то так....</small>
        </div>
    </div>
</div>

<!-- Локальный Bootstrap JS -->
<script src="/webjars/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

<script>
    // Автофокус на поле имени
    document.addEventListener('DOMContentLoaded', function() {
        const playerNameField = document.getElementById('playerName');
        if (playerNameField) {
            playerNameField.focus();
        }
    });
</script>
</body>
</html>