package step.formbot.controller.dispatcher.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.enums.UserState;

@Component
public class WritingSurveyNameHandler implements UpdateHandler {

    @Override
    public boolean isHandle(Update update, UserState userState) {
        return isText(update) && userState.equals(UserState.WRITING_SURVEY_NAME);
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {

        return null;
    }
}
