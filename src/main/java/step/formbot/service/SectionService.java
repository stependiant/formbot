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

    public Section getById(Long sectionId) {
        return sectionRepository.getSectionById(sectionId)
                .orElse(null);
    }

    public Long extractSectionId(String callbackData) {
        try {
            return Long.parseLong(callbackData.replace("section_show_", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
