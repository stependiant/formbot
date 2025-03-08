package step.formbot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import step.formbot.model.Answer;
import step.formbot.model.Question;
import step.formbot.model.Section;
import step.formbot.model.Survey;
import step.formbot.model.Topic;
import step.formbot.model.constants.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineKeyboardFactory {

    private static InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    private static List<InlineKeyboardButton> createRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(Arrays.asList(buttons));
    }

    private static InlineKeyboardMarkup createKeyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup createStartKeyboard(String buttonText, String callbackData) {
        InlineKeyboardButton button = createButton(buttonText, callbackData);
        List<InlineKeyboardButton> row = createRow(button);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        return createKeyboard(keyboard);
    }

    public static InlineKeyboardMarkup createMenuKeyboard() {
        InlineKeyboardButton btn1 = createButton("Новая характеристика", Callback.SURVEY_START_NEW);
        InlineKeyboardButton btn2 = createButton("Продолжить предыдущую характеристику", Callback.SURVEY_RESUME);
        InlineKeyboardButton btn3 = createButton("Вывести характеристику в word", Callback.EXPORT_SURVEY_IN_WORD);
        InlineKeyboardButton btn4 = createButton("Передать характеристику", Callback.SURVEY_EXPORT_SHOW_ALL);
        List<InlineKeyboardButton> row1 = createRow(btn1);
        List<InlineKeyboardButton> row2 = createRow(btn2);
        List<InlineKeyboardButton> row3 = createRow(btn3);
        List<InlineKeyboardButton> row4 = createRow(btn4);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        return createKeyboard(keyboard);
    }

    public static InlineKeyboardMarkup createSurveyListKeyboard(List<Survey> surveys) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (Survey survey : surveys) {
            InlineKeyboardButton button = createButton(
                    survey.getName(),
                    String.format(Callback.SURVEY_SHOW_BY_ID, survey.getId()));
            keyboard.add(createRow(button));
        }
        return createKeyboard(keyboard);
    }

    public static InlineKeyboardMarkup createSectionKeyboard(List<Section> sections) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (Section section : sections) {
            InlineKeyboardButton button = createButton(
                    section.getName(),
                    String.format(Callback.SECTION_SHOW_BY_ID, section.getId())
            );
            keyboard.add(createRow(button));
        }
        return createKeyboard(keyboard);
    }

    public static InlineKeyboardMarkup createTopicKeyboard(List<Topic> topics) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (Topic topic : topics) {
            InlineKeyboardButton button = createButton(
                    topic.getName(),
                    String.format(Callback.TOPIC_SHOW_BY_ID, topic.getId())
            );
            keyboard.add(createRow(button));
        }
        return createKeyboard(keyboard);
    }

    public static InlineKeyboardMarkup createAnswerKeyboardWithNavigation(
            Question question, Question prevQuestion, Question nextQuestion) {

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Answer answer : question.getAnswers()) {
            InlineKeyboardButton button = createButton(
                    answer.getText(),
                    String.format("answer_%d_question_%d", answer.getId(), question.getId())
            );
            keyboard.add(createRow(button));
        }

        List<InlineKeyboardButton> navigationButtons = new ArrayList<>();
        if (prevQuestion != null) {
            // Передаём id предыдущего вопроса
            navigationButtons.add(createButton("⬅️ Предыдущий", String.format("question_nav_%d", prevQuestion.getId())));
        }
        if (nextQuestion != null) {
            // Передаём id следующего вопроса
            navigationButtons.add(createButton("Следующий ➡️", String.format("question_nav_%d", nextQuestion.getId())));
        }

        if (!navigationButtons.isEmpty()) {
            keyboard.add(navigationButtons);
        }

        return createKeyboard(keyboard);
    }

}