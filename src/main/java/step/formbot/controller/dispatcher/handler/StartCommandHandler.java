package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.User;
import step.formbot.model.enums.UserStatus;
import step.formbot.service.UserService;
import step.formbot.util.MessageUtils;

@RequiredArgsConstructor
public class StartCommandHandler implements UpdateHandler {
    private final UserService userService;

    @Override
    public boolean supports(Update update, UserStatus userStatus) {
        return update.hasMessage()
                && update.getMessage().getText().equalsIgnoreCase("/start");
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserStatus userStatus) {
        Long chatId = update.getMessage().getChatId();
        User user = userService.getUserByChatId(chatId);

        if (user == null) {
            userService.createUser(update);
            return MessageUtils.createStartMessage(chatId);
        }
        return null;
    }
}
