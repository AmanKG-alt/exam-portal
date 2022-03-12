package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //add quiz service
    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        return  ResponseEntity.ok(this.quizService.addQuiz(quiz));
    }

    //update quiz
    @PutMapping("/")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
    }

    //get quiz
    @GetMapping("/")
    public ResponseEntity<?> quizzes(){
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    //get single quiz
    @GetMapping("/{qid}")
    public Quiz quiz(@PathVariable("qid") Long qid){
        return  this.quizService.getQuiz(qid);
    }

    //delete quiz
    @DeleteMapping("/{qid}")
    public void deleteQuiz(@PathVariable("qid") Long qid){
        this.quizService.deleteQuiz(qid);
    }

    //get quiz of category by id
    @GetMapping("/category/{cid}")
    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid){
        Category category =new Category();
        category.setCid(cid);
        return this.quizService.getQuizzesOfCategory(category);
    }

    //grt active quiz
    @GetMapping("/active")
    public List<Quiz> getActiveQuizzes(){
        return this.quizService.getActiveQuizzes();
    }

    //grt active quizzes of categories
    @GetMapping("/category/active/{cid}")
    public List<Quiz> getActiveQuizzes(@PathVariable("cid") Long cid){
        Category category=new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(category);
    }
}
