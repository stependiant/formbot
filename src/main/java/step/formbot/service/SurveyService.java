package step.formbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import step.formbot.model.Survey;
import step.formbot.repository.SurveyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }
}
