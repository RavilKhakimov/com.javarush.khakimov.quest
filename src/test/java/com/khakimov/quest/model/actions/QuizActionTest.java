package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizActionTest {

    @Test
    void shouldCreateQuizWithQuestionsAndOptions() {
        // Given
        QuizAction quiz = new QuizAction(1, "Ответить на вопрос",
                "Сколько будет 2+2?",
                List.of("3", "4", "5"),
                1, 2);

        // Then
        assertEquals("Сколько будет 2+2?", quiz.getQuestion());
        assertEquals(3, quiz.getOptions().size());
        assertEquals(1, quiz.getCorrectOptionIndex());
        assertEquals("4", quiz.getOptions().get(1));
    }

    @Test
    void shouldReturnNullOnExecute() {
        // Given
        QuizAction quiz = new QuizAction(1, "Тест", "Вопрос?", List.of("Да", "Нет"), 0, 1);
        PlayerState playerState = new PlayerState();

        // When
        var result = quiz.execute(playerState);

        // Then
        assertNull(result); // Согласно вашей текущей реализации
    }
}
