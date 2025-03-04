package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}