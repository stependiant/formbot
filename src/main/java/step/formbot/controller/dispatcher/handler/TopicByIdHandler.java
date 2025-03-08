package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.Question;
import step.formbot.model.Topic;
import step.formbot.model.enums.UserState;
import step.formbot.repository.postgres.TopicRepository;
import step.formbot.util.InlineKeyboardFactory;
import step.formbot.util.MessageUtils;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicByIdHandler implements UpdateHandler {
    private final TopicRepository topicRepository;

    @Override
    public boolean isHandle(Update update, UserState userState) {
        String callbackData = getCallbackData(update);
        return callbackData != null && callbackData.startsWith("topic_show_");
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);
        int messageId = getMessageId(update);
        Long topicId = extractTopicId(getCallbackData(update));

        Topic topic = topicRepository.findByIdWithQuestions(topicId)
                .orElse(null);

        if (topic == null || topic.getQuestions().isEmpty()) {
            return MessageUtils.createTextMessage(chatId, "Вопросы не найдены.");
        }

        List<Question> questions = topic.getQuestions();
        questions.sort(Comparator.comparing(Question::getId));

        Question question = questions.get(0);
        Question nextQuestion = questions.size() > 1 ? questions.get(1) : null;

        return MessageUtils.editKeyboardMessage(
                chatId,
                messageId,
                question.getText(),
                InlineKeyboardFactory.createAnswerKeyboardWithNavigation(question, null, nextQuestion)
        );
    }

    private Long extractTopicId(String callbackData) {
        try {
            return Long.parseLong(callbackData.replace("topic_show_", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}