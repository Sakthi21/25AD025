package _AD025.rev.dto;

public class AttemptResultDTO {

    private Long attemptId;
    private String studentName;
    private String quizTitle;
    private Integer score;
    private Integer totalQuestions;
    private Integer answeredQuestions;
    private String status;
    public AttemptResultDTO() {
    }
    public AttemptResultDTO(
            Long attemptId,
            String studentName,
            String quizTitle,
            Integer score,
            Integer totalQuestions,
            Integer answeredQuestions,
            String status) {

        this.attemptId = attemptId;
        this.studentName = studentName;
        this.quizTitle = quizTitle;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.answeredQuestions = answeredQuestions;
        this.status = status;
    }
    public Long getAttemptId() {
        return attemptId;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getQuizTitle() {
        return quizTitle;
    }
    public Integer getScore() {
        return score;
    }
    public Integer getTotalQuestions() {
        return totalQuestions;
    }
    public Integer getAnsweredQuestions() {
        return answeredQuestions;
    }
    public String getStatus() {
        return status;
    }
}