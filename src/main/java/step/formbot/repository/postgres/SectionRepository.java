package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {
}