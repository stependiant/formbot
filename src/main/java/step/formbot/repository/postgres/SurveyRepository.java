package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
