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
        return data != null && data.matches("question_\\d+_(next|prev)");
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);
        int messageId = getMessageId(update);
        String callbackData = getCallbackData(update);

        // Парсим ID и направление из колбэка
        String[] parts = callbackData.split("_");
        Long currentQuestionId = Long.parseLong(parts[1]);
        String direction = parts[2];

        // Получаем текущий вопрос
        Question currentQuestion = questionRepository.findById(currentQuestionId).orElse(null);
        if (currentQuestion == null) {
            return MessageUtils.createTextMessage(chatId, "Вопрос не найден.");
        }

        // Находим следующий или предыдущий вопрос
        Question targetQuestion = findAdjacentQuestion(currentQuestion, direction);
        if (targetQuestion == null) {
            return MessageUtils.createTextMessage(chatId, "Больше вопросов нет.");
        }

        boolean hasPrev = hasAdjacentQuestion(targetQuestion, "prev");
        boolean hasNext = hasAdjacentQuestion(targetQuestion, "next");

        // Возвращаем обновлённое сообщение с вопросом и ответами
        return MessageUtils.editKeyboardMessage(
                chatId,
                messageId,
                targetQuestion.getText(),
                InlineKeyboardFactory.createAnswerKeyboardWithNavigation(targetQuestion, hasPrev, hasNext)
        );
    }

    private Question findAdjacentQuestion(Question current, String direction) {
        List<Question> questions = questionRepository.findByTopicIdOrderById(current.getTopic().getId());

        int currentIndex = questions.indexOf(current);
        if (direction.equals("next") && currentIndex < questions.size() - 1) {
            return questions.get(currentIndex + 1);
        } else if (direction.equals("prev") && currentIndex > 0) {
            return questions.get(currentIndex - 1);
        }
        return null;
    }

    private boolean hasAdjacentQuestion(Question current, String direction) {
        List<Question> questions = questionRepository.findByTopicIdOrderById(current.getTopic().getId());
        int currentIndex = questions.indexOf(current);
        return (direction.equals("next") && currentIndex < questions.size() - 1) ||
                (direction.equals("prev") && currentIndex > 0);
    }
}
