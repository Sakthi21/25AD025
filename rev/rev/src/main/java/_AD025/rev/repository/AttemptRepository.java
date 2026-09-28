package _AD025.rev.repository;

import _AD025.rev.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Optional<Attempt> findByStudentIdAndQuizId(Long studentId, Long quizId);

    List<Attempt> findByQuizId(Long quizId);
}