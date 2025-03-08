package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import step.formbot.model.Topic;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.questions WHERE t.id = :id")
    Optional<Topic> findByIdWithQuestions(@Param("id") Long id);
}