package step.formbot.controller.dispatcher;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.enums.UserStatus;

public interface UpdateHandler {
    boolean supports(Update update, UserStatus userStatus);
    BotApiMethod<?> handle(Update update, UserStatus userStatus);
}
