package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.Survey;
import step.formbot.model.constants.Callback;
import step.formbot.model.enums.UserState;
import step.formbot.service.UserService;
import step.formbot.util.InlineKeyboardFactory;
import step.formbot.util.MessageUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuDispatcherHandler implements UpdateHandler {
    private final UserService userService;

    @Override
    public boolean isHandle(Update update, UserState userState) {
        return isCallback(update, Callback.SURVEY_START_NEW)
                || isCallback(update, Callback.SURVEY_RESUME)
                || isCallback(update, Callback.EXPORT_SURVEY_IN_WORD)
                || isCallback(update, Callback.SURVEY_EXPORT_SHOW_ALL);
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);
        int messageId = getMessageId(update);

        switch (getCallbackData(update)) {
            case Callback.SURVEY_START_NEW -> {
                userService.setUserState(chatId, UserState.WRITING_SURVEY_NAME);
                return MessageUtils.editNewSurveyMessage(chatId, messageId);
            }
            case Callback.SURVEY_RESUME -> {
                List<Survey> surveys = userService.getSurveysByChatId(chatId);
                if (surveys.isEmpty())
                    return MessageUtils.createTextMessage(chatId, "У вас пока что нету опросов");
                return MessageUtils.editKeyboardMessage(chatId, messageId, "Выберите опрос для продолжения:",
                        InlineKeyboardFactory.createSurveyListKeyboard(surveys));
            }
            case Callback.EXPORT_SURVEY_IN_WORD -> {
                //TODO: 1
            }
            case Callback.SURVEY_EXPORT_SHOW_ALL -> {
                //TODO: 2
            }
        }
        return null;
    }
}
