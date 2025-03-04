package step.formbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import step.formbot.util.MessageUtils;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }
    @Override
    public String getBotUsername() {
        return "FormBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            log.debug("message from {} with text {}", chatId, messageText);

        }
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = MessageUtils.createMessage(chatId, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("cant send message:{} in chat id: {}", text, chatId);
        }
    }

    @Bean
    public TelegramBotsApi initBot() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(this);
        return api;
    }
}
