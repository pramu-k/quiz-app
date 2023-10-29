package com.pramuk.quizapp.services;

import com.pramuk.quizapp.domain.entities.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    public ResponseEntity<List<Question>>  findAllQuestions();

    ResponseEntity<List<Question>> getQuestionsByCategory(String category);

    ResponseEntity<String> addQuestion(Question question);

    ResponseEntity deleteQuestionById(Integer id);

    List<Question> getRandomQuestionsByCategory(String category, int numQ);

    ResponseEntity<Question> fullUpdateQuestion(Integer id, Question question);

    ResponseEntity<Question> partialUpdateQuestion(Integer id, Question question);
}
