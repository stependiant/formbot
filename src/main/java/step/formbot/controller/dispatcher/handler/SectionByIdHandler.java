package step.formbot.controller.dispatcher.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.enums.UserState;

public class SectionByIdHandler implements UpdateHandler {
    @Override
    public boolean isHandle(Update update, UserState userState) {
        return false;
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        return null;
    }
}
