package com.pramuk.quizapp.services.impl;

import com.pramuk.quizapp.dao.QuestionDao;
import com.pramuk.quizapp.domain.entities.Question;
import com.pramuk.quizapp.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ResponseEntity<List<Question>>  findAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public  ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> addQuestion(Question question) {

        try{
            questionDao.save(question);
            return new ResponseEntity<>("success!", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Unsuccessful!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity deleteQuestionById(Integer id) {
        if(questionDao.existsById(id)){
            try{
                questionDao.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Question> getRandomQuestionsByCategory(String category, int numQ) {
        return questionDao.getRandomQuestionsByCategory(category,numQ);
    }

    @Override
    public ResponseEntity<Question> fullUpdateQuestion(Integer id, Question question) {
        if(questionDao.existsById(id)){
            Question savedQuestion = questionDao.save(question);
            return new ResponseEntity<>(savedQuestion,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<Question> partialUpdateQuestion(Integer id, Question question) {
        question.setId(id);
        Optional<Question> updatedQuestion = questionDao.findById(id).map(selectedQuestion -> {
            Optional.ofNullable(question.getQuestionTitle()).ifPresent(selectedQuestion::setQuestionTitle);
            Optional.ofNullable(question.getOption1()).ifPresent(selectedQuestion::setOption1);
            Optional.ofNullable(question.getOption2()).ifPresent(selectedQuestion::setOption2);
            Optional.ofNullable(question.getOption3()).ifPresent(selectedQuestion::setOption3);
            Optional.ofNullable(question.getOption4()).ifPresent(selectedQuestion::setOption4);
            Optional.ofNullable(question.getRightAnswer()).ifPresent(selectedQuestion::setRightAnswer);
            Optional.ofNullable(question.getDifficultyLevel()).ifPresent(selectedQuestion::setDifficultyLevel);
            Optional.ofNullable(question.getCategory()).ifPresent(selectedQuestion::setCategory);
            return questionDao.save(selectedQuestion);
        });
        if(updatedQuestion.isPresent()){
            return new ResponseEntity<>(updatedQuestion.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
