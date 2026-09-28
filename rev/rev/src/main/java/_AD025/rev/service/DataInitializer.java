package _AD025.rev.service;

import _AD025.rev.entity.Answer;
import _AD025.rev.entity.Question;
import _AD025.rev.entity.Quiz;
import _AD025.rev.repository.QuizRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final QuizRepository quizRepository;

    public DataInitializer(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void run(String... args) {

        // Do not insert duplicate questions
        if (quizRepository.count() > 0) {
            return;
        }

        // Create quiz
        Quiz quiz = new Quiz();

        quiz.setTitle("Java Basics");
        quiz.setCategory("Programming");
        quiz.setTimeLimitMinutes(5);


        // Question 1
        Question q1 = createQuestion(
                "What is Java?",
                "Programming Language",
                true,
                "Operating System",
                false,
                "Database",
                false,
                "Web Browser",
                false
        );

        // Question 2
        Question q2 = createQuestion(
                "Which keyword is used for inheritance in Java?",
                "extends",
                true,
                "inherit",
                false,
                "implements",
                false,
                "super",
                false
        );

        // Question 3
        Question q3 = createQuestion(
                "Which method is the starting point of a Java program?",
                "main()",
                true,
                "start()",
                false,
                "run()",
                false,
                "begin()",
                false
        );

        // Question 4
        Question q4 = createQuestion(
                "Which of the following is a primitive data type in Java?",
                "int",
                true,
                "String",
                false,
                "Array",
                false,
                "Object",
                false
        );

        // Question 5
        Question q5 = createQuestion(
                "Which symbol is used to end a statement in Java?",
                ";",
                true,
                ":",
                false,
                ".",
                false,
                ",",
                false
        );


        // Connect questions with quiz
        q1.setQuiz(quiz);
        q2.setQuiz(quiz);
        q3.setQuiz(quiz);
        q4.setQuiz(quiz);
        q5.setQuiz(quiz);

        quiz.getQuestions().add(q1);
        quiz.getQuestions().add(q2);
        quiz.getQuestions().add(q3);
        quiz.getQuestions().add(q4);
        quiz.getQuestions().add(q5);


        // Save everything
        quizRepository.save(quiz);

        System.out.println("Fixed quiz data inserted successfully!");
    }


    private Question createQuestion(
            String questionText,
            String answer1, boolean correct1,
            String answer2, boolean correct2,
            String answer3, boolean correct3,
            String answer4, boolean correct4) {

        Question question = new Question();

        question.setQuestionText(questionText);

        addAnswer(question, answer1, correct1);
        addAnswer(question, answer2, correct2);
        addAnswer(question, answer3, correct3);
        addAnswer(question, answer4, correct4);

        return question;
    }


    private void addAnswer(
            Question question,
            String answerText,
            boolean correct) {

        Answer answer = new Answer();

        answer.setAnswerText(answerText);
        answer.setCorrect(correct);
        answer.setQuestion(question);

        question.getAnswers().add(answer);
    }
}