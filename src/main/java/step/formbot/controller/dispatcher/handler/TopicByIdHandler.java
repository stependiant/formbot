package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.Question;
import step.formbot.model.Topic;
import step.formbot.model.enums.UserState;
import step.formbot.repository.postgres.QuestionRepository;
import step.formbot.repository.postgres.TopicRepository;
import step.formbot.util.InlineKeyboardFactory;
import step.formbot.util.MessageUtils;

@Component
@RequiredArgsConstructor
public class TopicByIdHandler implements UpdateHandler {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

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

        Question question = topic.getQuestions().get(0);

        return MessageUtils.editKeyboardMessage(
                chatId,
                messageId,
                question.getText(),
                InlineKeyboardFactory.createAnswerKeyboardWithNavigation(question, false, true)
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
