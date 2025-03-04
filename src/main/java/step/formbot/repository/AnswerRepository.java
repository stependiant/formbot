package step.formbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
