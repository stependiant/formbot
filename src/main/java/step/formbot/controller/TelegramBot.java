package step.formbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import step.formbot.controller.dispatcher.UpdateDispatcher;
import step.formbot.model.enums.UserState;
import step.formbot.service.UserService;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final UpdateDispatcher updateDispatcher;
    private final UserService userService;

    public TelegramBot(@Value("${telegram.bot.token}") String botToken,
                       UpdateDispatcher updateDispatcher,
                       UserService userService) {
        super(botToken);
        this.updateDispatcher = updateDispatcher;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);
        if (chatId == null) return;
        UserState userState = userService.getUserStatusByChatId(chatId);
        BotApiMethod<?> response = updateDispatcher.dispatch(update, userState);
        if (response != null) {
            try {
                execute(response);
            } catch (TelegramApiException e) {
                log.error("Error while executing response: {} for update: {}", response, update, e);
            }
        }
    }

    private static Long getChatId(Update update) {
        Long chatId = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        return chatId;
    }

    @Override
    public String getBotUsername() {
        return "FormBot";
    }
}