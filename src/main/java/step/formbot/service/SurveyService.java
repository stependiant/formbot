package step.formbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.Survey;
import step.formbot.model.User;
import step.formbot.repository.postgres.SurveyRepository;

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

    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey create(String childName, User user) {
        Survey survey = Survey.builder()
                .name(childName)
                .user(user)
                .build();
        return save(survey);
    }
}
