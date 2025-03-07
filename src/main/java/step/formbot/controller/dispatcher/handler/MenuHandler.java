package step.formbot.controller.dispatcher.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.constants.Callback;
import step.formbot.model.enums.UserState;
import step.formbot.util.MessageUtils;

@Component
public class MenuHandler implements UpdateHandler {

    @Override
    public boolean isHandle(Update update, UserState userState) {
        return isTextCommand(update, "/menu")
                || isCallback(update, Callback.COMMAND_MENU);
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);

        return MessageUtils.createMenuMessage(chatId);
    }
}
