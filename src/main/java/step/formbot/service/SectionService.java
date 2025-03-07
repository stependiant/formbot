package step.formbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import step.formbot.model.Section;
import step.formbot.repository.postgres.SectionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;

    public List<Section> getAll() {
        return sectionRepository.findAll();
    }
}
