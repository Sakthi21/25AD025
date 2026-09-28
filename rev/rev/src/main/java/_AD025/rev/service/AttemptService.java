package _AD025.rev.service;

import _AD025.rev.dto.AnswerSubmission;
import _AD025.rev.dto.AttemptResultDTO;
import _AD025.rev.dto.StartAttemptRequest;
import _AD025.rev.dto.SubmitAttemptRequest;
import _AD025.rev.entity.Answer;
import _AD025.rev.entity.Attempt;
import _AD025.rev.entity.Question;
import _AD025.rev.entity.Quiz;
import _AD025.rev.entity.Student;
import _AD025.rev.enums.AttemptStatus;
import _AD025.rev.repository.AnswerRepository;
import _AD025.rev.repository.AttemptRepository;
import _AD025.rev.repository.QuestionRepository;
import _AD025.rev.repository.QuizRepository;
import _AD025.rev.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final StudentRepository studentRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AttemptService(
            AttemptRepository attemptRepository,
            StudentRepository studentRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository) {

        this.attemptRepository = attemptRepository;
        this.studentRepository = studentRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    // Start a quiz attempt
    public Attempt startAttempt(Long quizId, StartAttemptRequest request) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {

                    Student newStudent = new Student();

                    newStudent.setName(request.getName());
                    newStudent.setEmail(request.getEmail());

                    return studentRepository.save(newStudent);
                });

        // Check whether student already attempted this quiz
        if (attemptRepository
                .findByStudentIdAndQuizId(student.getId(), quizId)
                .isPresent()) {

            throw new RuntimeException(
                    "Student has already attempted this quiz");
        }

        List<Question> questions =
                questionRepository.findByQuizId(quizId);

        Attempt attempt = new Attempt();

        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setStartedAt(LocalDateTime.now());

        attempt.setTotalQuestions(questions.size());
        attempt.setAnsweredQuestions(0);
        attempt.setScore(0);

        attempt.setStatus(AttemptStatus.IN_PROGRESS);

        return attemptRepository.save(attempt);
    }


    // Submit quiz
    public AttemptResultDTO submitAttempt(
            Long attemptId,
            SubmitAttemptRequest request) {

        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException("Attempt not found"));

        // Prevent submitting twice
        if (attempt.getStatus() == AttemptStatus.SUBMITTED) {
            throw new RuntimeException(
                    "Attempt already submitted");
        }

        int score = 0;
        int answered = 0;

        for (AnswerSubmission submission : request.getAnswers()) {

            if (submission.getAnswerId() == null) {
                continue;
            }

            answered++;

            Answer answer = answerRepository
                    .findById(submission.getAnswerId())
                    .orElseThrow(() ->
                            new RuntimeException("Answer not found"));

            // Check whether selected answer is correct
            if (answer.isCorrect()) {
                score++;
            }
        }

        attempt.setScore(score);
        attempt.setAnsweredQuestions(answered);
        attempt.setSubmittedAt(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.SUBMITTED);

        attemptRepository.save(attempt);

        return new AttemptResultDTO(
                attempt.getId(),
                attempt.getStudent().getName(),
                attempt.getQuiz().getTitle(),
                score,
                attempt.getTotalQuestions(),
                answered,
                attempt.getStatus().name()
        );
    }


    // Get report for a quiz
    public List<Attempt> getQuizReport(Long quizId) {

        return attemptRepository.findByQuizId(quizId);
    }
}