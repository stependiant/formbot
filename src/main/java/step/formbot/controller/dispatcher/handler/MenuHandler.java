package step.formbot.controller.dispatcher.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.constants.Callback;
import step.formbot.model.enums.UserStatus;
import step.formbot.util.MessageUtils;

@Component
public class MenuHandler implements UpdateHandler {

    @Override
    public boolean supports(Update update, UserStatus userStatus) {
        if (update.hasMessage() && update.getMessage().hasText() &&
                update.getMessage().getText().trim().equalsIgnoreCase("/menu")) {
            return true;
        }
        return update.hasCallbackQuery() &&
                update.getCallbackQuery().getData().equals(Callback.COMMAND_MENU);
    }


    @Override
    public BotApiMethod<?> handle(Update update, UserStatus userStatus) {
        Long chatId = getChatId(update);
        return MessageUtils.createMenuMessage(chatId);
    }
}
