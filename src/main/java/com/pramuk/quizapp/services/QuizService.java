package com.pramuk.quizapp.services;

import com.pramuk.quizapp.domain.entities.QuestionWrapper;
import com.pramuk.quizapp.domain.entities.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizService {
    public ResponseEntity<String> createQuiz(String category, int numQ, String title);

    ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id);

    ResponseEntity<String> calculateResult(Integer id, List<UserResponse> responses);
}
