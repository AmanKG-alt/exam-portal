package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.services.QuestionService;
import com.exam.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    //add question
    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        return  ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //update question
    @PutMapping("/")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question){
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //get all question of any qid
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
       /* *//*all  questions of quiz*//*
        Quiz quiz= new Quiz();
        quiz.setQId(qid);
     Set<Question> questionsOfQuiz= this.questionService.getQuestionsOfQuiz(quiz);
     return ResponseEntity.ok(questionsOfQuiz);*/
        Quiz quiz=this.quizService.getQuiz(qid);
        Set<Question> questions=quiz.getQuestions();
        List<Question> list= new ArrayList(questions);
        if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
            list=list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }

        list.forEach((q)->{
            q.setAnswer("");
        });

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    //*all  questions of quiz*//*
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid){

        Quiz quiz= new Quiz();
        quiz.setQId(qid);
     Set<Question> questionsOfQuiz= this.questionService.getQuestionsOfQuiz(quiz);
     return ResponseEntity.ok(questionsOfQuiz);
//        Quiz quiz=this.quizService.getQuiz(qid);
//        Set<Question> questions=quiz.getQuestions();
//        List list= new ArrayList(questions);
//        if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
//            list=list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
//        }
//        Collections.shuffle(list);
//        return ResponseEntity.ok(list);
    }



    //get single question
    @GetMapping("/{quesId}")
    public Question get(@PathVariable("quesId") Long quesId){
        return this.questionService.getQuestion(quesId);
    }

    //delete question
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId){
        this.questionService.deleteQuestion(quesId);
    }


    //eval quiz
    @PostMapping("/eval-quiz")
    public  ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
        System.out.println(questions);
        double marksGot=0;
        double correctAnswer=0;
        int attempted=0;

        for(Question q:questions){
            //single questions
            Question question=this.questionService.get(q.getQuesId());
            if(question.getAnswer().trim().equals(q.getGivenAnswer().trim())){
                correctAnswer++;

                double marksSingle=Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();

                marksGot += marksSingle;
            }

            if(q.getGivenAnswer()!=null){
                attempted++;
            }
        };
        Map<String, Object> of= Map.of("marksGot", marksGot, "correctAnswer", correctAnswer,"attempted",attempted);
        return ResponseEntity.ok(of);
    }

}
