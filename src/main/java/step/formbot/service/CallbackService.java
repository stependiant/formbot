package step.formbot.service;

import org.springframework.stereotype.Service;
import step.formbot.model.constants.Callback;

@Service
public class CallbackService {

    public String getCommandMenu() {
        return Callback.COMMAND_MENU;
    }

    public String getSurveyStartNew() {
        return Callback.SURVEY_START_NEW;
    }

    public String getSurveyResume() {
        return Callback.SURVEY_RESUME;
    }

    public String getSurveyExportShowAll() {
        return Callback.SURVEY_EXPORT_SHOW_ALL;
    }

    public String getSurveyShowById(long surveyId) {
        return String.format(Callback.SURVEY_SHOW_BY_ID, surveyId);
    }

    public String getSurveyExportShowById(long surveyId) {
        return String.format(Callback.SURVEY_EXPORT_SHOW_BY_ID, surveyId);
    }

    public String getExportSurveyBySurveyIdToUserId(long surveyId, long userId) {
        return String.format(Callback.EXPORT_SURVEY_BY_SURVEY_ID_TO_USER_ID, surveyId, userId);
    }

    public String getSectionShowById(long sectionId) {
        return String.format(Callback.SECTION_SHOW_BY_ID, sectionId);
    }

    public String getTopicShowById(long topicId) {
        return String.format(Callback.TOPIC_SHOW_BY_ID, topicId);
    }

    public String getQuestionByQuestionIdInTopicByTopicId(long questionId, long topicId) {
        return String.format(Callback.QUESTION_BY_QUESTION_ID_IN_TOPIC_BY_TOPIC_ID, questionId, topicId);
    }

    public String getExportSurveyInWord() {
        return Callback.EXPORT_SURVEY_IN_WORD;
    }
}