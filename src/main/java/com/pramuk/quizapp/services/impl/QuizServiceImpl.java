package com.pramuk.quizapp.services.impl;

import com.pramuk.quizapp.dao.QuizDao;
import com.pramuk.quizapp.domain.entities.Question;
import com.pramuk.quizapp.domain.entities.QuestionWrapper;
import com.pramuk.quizapp.domain.entities.Quiz;
import com.pramuk.quizapp.domain.entities.UserResponse;
import com.pramuk.quizapp.services.QuestionService;
import com.pramuk.quizapp.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    private QuestionService questionService;
    private QuizDao quizDao;

    public QuizServiceImpl(QuestionService questionService, QuizDao quizDao) {
        this.questionService = questionService;
        this.quizDao = quizDao;
    }

    @Override
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questionsList=questionService.getRandomQuestionsByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionsList);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> selectedQuiz = quizDao.findById(id);
        List<Question> questionsFromDb=selectedQuiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser=new ArrayList<>();
        questionsFromDb.forEach(question -> {
            QuestionWrapper questionWrapper=new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            );
            questionsForUser.add(questionWrapper);
        });
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> calculateResult(Integer id, List<UserResponse> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isPresent()){
            int rightAnswers=0;
            List<Question> quizQuestions=quiz.get().getQuestions();
            for (int i = 0; i < responses.toArray().length; i++) {
                if(responses.get(i).getResponse().equals(quizQuestions.get(i).getRightAnswer())){
                    rightAnswers+=1;
                }
            }
            return new ResponseEntity<>(String.valueOf(rightAnswers),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Quiz Found",HttpStatus.NOT_FOUND);
        }

    }
}
