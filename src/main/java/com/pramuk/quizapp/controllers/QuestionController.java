package com.pramuk.quizapp.controllers;
import com.pramuk.quizapp.domain.entities.Question;
import com.pramuk.quizapp.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.findAllQuestions();
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category")String category){
        return questionService.getQuestionsByCategory(category);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Question> fullUpdateQuestion(@PathVariable Integer id, @RequestBody Question question){
        return questionService.fullUpdateQuestion(id,question);

    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<Question> partialUpdateQuestion(@PathVariable Integer id, @RequestBody Question question){
        return questionService.partialUpdateQuestion(id,question);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestionById(id);
    }
}
