package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.Question;
import step.formbot.model.enums.UserState;
import step.formbot.repository.postgres.QuestionRepository;
import step.formbot.util.InlineKeyboardFactory;
import step.formbot.util.MessageUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionNavigationHandler implements UpdateHandler {

    private final QuestionRepository questionRepository;

    @Override
    public boolean isHandle(Update update, UserState userState) {
        String data = getCallbackData(update);
        return data != null && data.matches("question_nav_\\d+");
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);
        int messageId = getMessageId(update);
        String callbackData = getCallbackData(update);

        Long targetQuestionId = Long.parseLong(callbackData.replace("question_nav_", ""));
        Question targetQuestion = questionRepository.findById(targetQuestionId).orElse(null);
        if (targetQuestion == null) {
            return MessageUtils.createTextMessage(chatId, "Вопрос не найден.");
        }

        List<Question> questions = questionRepository.findByTopicIdOrderById(targetQuestion.getTopic().getId());
        int currentIndex = questions.indexOf(targetQuestion);
        Question prevQuestion = currentIndex > 0 ? questions.get(currentIndex - 1) : null;
        Question nextQuestion = currentIndex < questions.size() - 1 ? questions.get(currentIndex + 1) : null;

        return MessageUtils.editKeyboardMessage(
                chatId,
                messageId,
                targetQuestion.getText(),
                InlineKeyboardFactory.createAnswerKeyboardWithNavigation(targetQuestion, prevQuestion, nextQuestion)
        );
    }
}
