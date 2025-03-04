package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}
