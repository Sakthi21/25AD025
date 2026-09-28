package _AD025.rev.controller;

import _AD025.rev.entity.Quiz;
import _AD025.rev.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }
}