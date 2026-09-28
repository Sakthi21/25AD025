package _AD025.rev.dto;
import java.util.List;
public class SubmitAttemptRequest {
    private List<AnswerSubmission> answers;
    public SubmitAttemptRequest() {
    }
    public List<AnswerSubmission> getAnswers() {
        return answers;
    }
    public void setAnswers(List<AnswerSubmission> answers) {
        this.answers = answers;
    }
}