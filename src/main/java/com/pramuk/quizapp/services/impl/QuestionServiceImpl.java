package com.pramuk.quizapp.services.impl;

import com.pramuk.quizapp.dao.QuestionDao;
import com.pramuk.quizapp.domain.entities.Question;
import com.pramuk.quizapp.services.QuestionService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
