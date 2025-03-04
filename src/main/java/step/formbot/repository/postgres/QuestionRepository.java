package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}