package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.User;
import step.formbot.model.enums.UserStatus;
import step.formbot.service.UserService;
import step.formbot.util.MessageUtils;

@Component
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
        Long chatId = getChatId(update);
        User user = userService.getUserByChatId(chatId);

        if (user == null) {
            user = userService.createUser(update);
            userService.updateUser(user);
            return MessageUtils.createStartMessage(chatId);
        }
        return MessageUtils.createTextMessage(chatId, "Бот уже запущен. Выберите действие из меню.");
    }
}
