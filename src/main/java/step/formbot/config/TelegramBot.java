package step.formbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.util.MessageUtils;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                handleTextMessage(update);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        } else {
            log.error("Необработанный тип обновления: {}", update);
        }
    }

    private void handleTextMessage(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if (messageText.startsWith("/")) {
            handleCommand(chatId, messageText);
        } else {
            MessageUtils.sendTextMessage(this, chatId, "Вы написали: " + messageText);
        }
        log.debug("Text message from {}: {}", chatId, messageText);
    }

    private void handleCommand(Long chatId, String command) {
        switch (command) {
            case "/start" -> MessageUtils.sendTextMessage(this, chatId, "Добро пожаловать!");
            case "/help" -> MessageUtils.sendTextMessage(this, chatId, "Список команд: /start, /help, ...");
            default -> MessageUtils.sendTextMessage(this, chatId, "Неизвестная команда: " + command);
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        MessageUtils.sendTextMessage(this, chatId, "Callback получен: " + callbackData);
        log.debug("CallbackQuery from {}: {}", chatId, callbackData);
    }

    @Override
    public String getBotUsername() {
        return "FormBot";
    }
}