package _AD025.rev.controller;

import _AD025.rev.dto.AttemptResultDTO;
import _AD025.rev.dto.StartAttemptRequest;
import _AD025.rev.dto.SubmitAttemptRequest;
import _AD025.rev.entity.Attempt;
import _AD025.rev.service.AttemptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/quiz/{quizId}/start")
    public Attempt startAttempt(
            @PathVariable Long quizId,
            @RequestBody StartAttemptRequest request) {

        return attemptService.startAttempt(quizId, request);
    }

    @PostMapping("/{attemptId}/submit")
    public AttemptResultDTO submitAttempt(
            @PathVariable Long attemptId,
            @RequestBody SubmitAttemptRequest request) {

        return attemptService.submitAttempt(attemptId, request);
    }

    @GetMapping("/quiz/{quizId}/report")
    public List<Attempt> getReport(
            @PathVariable Long quizId) {

        return attemptService.getQuizReport(quizId);
    }
}